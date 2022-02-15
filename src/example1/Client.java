package example1;

import java.util.Date;

/**
 * client class
 *
 * @author tadaki
 */
public class Client implements Runnable {

    private final int id;
    private final Server server;
    private volatile boolean running = true;
    private int c = 0;//the number of connections
    public static final int Cmax = 5;//the maximum number of connections

    public Client(int id, Server server) {
        this.id = id;
        this.server = server;
    }

    /**
     * Connect to the server
     */
    private void connect() {
        Date date = new Date();
        server.register(this, c, date.toString());
        c++;
    }

    /**
     * connect to the server with random timing
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
