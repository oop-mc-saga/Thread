package example1;

import java.util.Date;

/**
 * クライアントクラス
 *
 * @author tadaki
 */
public class Client implements Runnable {

    private final int id;
    private final Server server;//接続先サーバ
    private volatile boolean running = true;
    private int c = 0;//接続回数をカウント
    public static final int Cmax = 5;//最大接続回数

    public Client(int id, Server server) {
        this.id = id;
        this.server = server;
    }

    /**
     * サーバへの接続の具体
     */
    private void connect() {
        Date date = new Date();
        server.register(this, c, date.toString());
        c++;
    }

    /**
     * ランダムな時間間隔でserverへアクセスする
     */
    @Override
    public void run() {
        while (running) {
            connect();
            int t = (int) (1000 * Math.random());
            try {
                Thread.sleep(t);
            } catch (InterruptedException e) {
            }
            if (Cmax <= c) {
                running = false;
            }
        }
    }

    @Override
    public String toString() {
        return "client-" + id;
    }
}
