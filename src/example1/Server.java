package example1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * サーバクラス
 *
 * @author tadaki
 */
public class Server implements Runnable {

    private final List<String> messageList;
    private final int max;
    private volatile boolean running = true;

    public Server(int max) {
        messageList = new ArrayList<>();
        this.max = max;
    }

    /**
     * 定期的にmessageListのサイズを調べ、終了を確認する
     */
    @Override
    public void run() {
        while (running) {
            synchronized (messageList) {//listのロックが外れるのを待つ
                if (messageList.size() == max) {
                    running = false;
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * クライアントの接続をmessageListへ登録する。 
     * 一度に一つのクライアントが利用できる。 
     * 1秒に一つしか登録できない
     *
     * @param client
     * @param c
     * @param dateStr
     */
    synchronized public void register(Client client, int c, String dateStr) {
        Date date = new Date();
        //クライアントが接続しようとした時刻と実際に接続した時刻を記録
        String ss = client + ":" + c + " " + dateStr + "->" + date.toString();
        messageList.add(ss);
        System.out.println(ss);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 5;//クライアント数
        Server server = new Server(n * Client.Cmax);
        //サーバをthreadとして起動
        new Thread(server).start();
        for (int i = 0; i < n; i++) {//クライアントをthreadとして起動
            new Thread(new Client(i, server)).start();
        }
    }

}
