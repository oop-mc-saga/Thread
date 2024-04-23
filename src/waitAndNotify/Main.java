package waitAndNotify;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class
 *
 * @author tadaki
 */
public class Main implements Runnable {

    final private int num;//number of targets
    final List<Target> targetList;//list of targets

    public Main(int num) {
        this.num = num;
        targetList = new ArrayList<>();
        //create targets and start threads
        for (int i = 0; i < num; i++) {
            Target t = new Target(i);
            new Thread(t).start();
            targetList.add(t);
        }
    }

    @Override
    public void run() {
        while (update()) {
            //The main thread sleeps for a while
            int duration = (int) (5000 * Math.random());
            try {
                Thread.sleep(duration);
            } catch (InterruptedException ex) {
            }
        }
    }

    private boolean update() {
        //notify all threads for targets
        targetList.forEach(t -> {
            synchronized (t) {
                if (t.isRunning()) {
                    t.notify();
                }
            }
        });
        //check if all targets are stopped
        int k = 0;
        for (Target tt : targetList) {
            if (!tt.isRunning()) {
                k++;
            }
        }
        return (k != num);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 2;
        new Thread(new Main(n)).start();
        // TODO code application logic here
    }

}
