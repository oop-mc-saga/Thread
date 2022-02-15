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
     * Confirm the size of messageList periodically 
     * and exit if the size reaches the max.
     */
    @Override
    public void run() {
        while (running) {
            //waiting the list unlocked
            synchronized (messageList) {
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
     * Register client connections to messageList.
     * Only one client is allowed to connect.
     * Only one client is allowed par one second.
     *
     * @param client connected client
     * @param c the number of connection by the client
     * @param dateStr The time client tries to connect
     */
    synchronized public void register(Client client,
            int c, String dateStr) {
        Date date = new Date();
        //The time the client tries to connect and succeeds to connect
        String ss = client + ":" + c + " "
                + dateStr + "->" + date.toString();
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
        int n = 5;//the number of clients
        Server server = new Server(n * Client.Cmax);
        //サーバをthreadとして起動
        new Thread(server).start();
        for (int i = 0; i < n; i++) {//start clients as threads
            new Thread(new Client(i, server)).start();
        }
    }

}
