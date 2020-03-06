package example3;

import example2.*;
import java.util.Date;

/**
 *
 * @author tadaki
 */
public class ServerExt extends Server {

    public ServerExt(int numClient, int maxSecond) {
        super(numClient, maxSecond);
    }

    @Override
    synchronized public void run() {
        while (running) {
            if (endClients.size() == numClient) {
                System.out.println("All clients close");
                running = false;
            }
            try {
                System.out.println("Server sleeps");
                wait(2000);
            } catch (InterruptedException ex) {
            }
            Date now = new Date();
            if (now.after(endTime)) {
                System.out.println("Server time out");
                running = false;
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
        notify();
        try {
            wait(1000);
        } catch (InterruptedException e) {
        }
        return b;
    }

    /**
     * クライアントがtokenを返す
     *
     * @param client
     * @param t
     */
    synchronized public boolean put(Client client, Token t) {
        if (running) {
            putSub(client, t);
            notify();
            try {
                wait(1000);
            } catch (InterruptedException e) {
            }
        }
        return running;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 5;
        Server server = new ServerExt(n, 60);
        new Thread(server).start();
        for (int i = 0; i < n; i++) {
            new Thread(new Client(i, server)).start();
        }
    }

}
