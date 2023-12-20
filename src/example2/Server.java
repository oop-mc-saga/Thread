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
    protected final Set<Client> endClients;
    protected final List<String> log;//communication record
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
        //Set the down time
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
            //stop this server at the endTime
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
     * client uses this method to get a token
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
     * client uses this method to put a token back
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
        sb.append("tokens at Server:[");
        if (!tokens.isEmpty()) {
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
