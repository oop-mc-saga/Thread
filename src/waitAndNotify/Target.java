package waitAndNotify;

import java.util.Date;

/**
 * Target class
 * 
 * @author tadaki
 */
public class Target implements Runnable {

    final int maxNumber = 10;//maximum number of iterations
    private int i = 0;//counter
    private volatile boolean running = true;
    final private int label;//label of the target

    public Target(int label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (running) {
            running = update();
        }
    }

    private synchronized boolean update() {
        i++;
        if (i >= maxNumber) {
            //stop if the number of iterations exceeds the maximum
            return false;
        }
        try {
            Date d = new Date();
            System.out.println(label + " stops at "  + d);
            wait();//wait for a while
            //output the current timestamp
            d = new Date();
            System.out.println(label + " restarts at "  + d);
        } catch (InterruptedException ex) {
        }
        return true;
    }

    public boolean isRunning() {
        return running;
    }
}
