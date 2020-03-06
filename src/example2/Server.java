package example2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author tadaki
 */
public class Server implements Runnable {

    protected final Queue<Token> tokens;
    public static final Token falseToken = new Token(-1);
    protected final int numClient;
    protected final Set<Client> endClients;//通信終了クライアント
    protected final List<String> log;//通信記録
    protected boolean running = true;
    protected final Date endTime;

    public Server(int numClient, int maxSecond) {
        this.numClient = numClient;
        log = new ArrayList<>();
        tokens = new LinkedList<>();
        for (int i = 0; i < numClient; i++) {
            tokens.add(new Token(i));
        }
        endClients = new HashSet<>();
        long startTime = new Date().getTime();
        //停止時刻設定
        endTime = new Date();
        endTime.setTime(startTime + maxSecond * 1000);
    }

    @Override
    public void run() {
        while (running) {
            if (endClients.size() == numClient) {
                System.out.println("All clients close");
                running = false;
            }
            //あらかじめ定めた時刻を過ぎたらサーバ停止
            Date now = new Date();
            if (now.after(endTime)) {
                running = false;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * クライアントがtokenを得る
     *
     * @param client
     * @return
     */
    synchronized public Token get(Client client) {
        Token b = getSub(client);       
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return b;
    }
    
    protected Token getSub(Client client){
        Date date = new Date();
        Token b = null;
        StringBuilder sb = new StringBuilder();
        sb.append(date).append(" ").append(client);
        if (!running) {
            sb.append(" close");
            endClients.add(client);
            b = falseToken;
        } else {
            if (tokens.isEmpty()) {
                sb.append(" no token");
            } else {
                b = tokens.poll();
                sb.append(" get ").append(b);
            }
        }
        sb.append(" ").append(token2str());
        System.out.println(sb.toString());
        return b;
    }

    /**
     * クライアントがtokenを返す
     *
     * @param client
     * @param t
     */
    synchronized boolean put(Client client, Token t) {
        if (running) {
            putSub(client, t);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        return running;
    }

    protected void putSub(Client client, Token t) {
        Date date = new Date();
        tokens.add(t);
        StringBuilder sb = new StringBuilder();
        sb.append(date).append(" ").append(client).append(" put ").append(t);
        sb.append(" ").append(token2str());
        log.add(sb.toString());
        System.out.println(sb.toString());
    }

    protected String token2str() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (tokens.size() > 0) {
            tokens.stream().forEachOrdered(t -> sb.append(t).append(","));
            int last = sb.lastIndexOf(",");
            sb.replace(last, last + 1, "]");
        } else {
            sb.append("]");
        }
        return sb.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 5;
        Server server = new Server(n, 60);
        new Thread(server).start();
        for (int i = 0; i < n; i++) {
            new Thread(new Client(i, server)).start();
        }
    }

}
