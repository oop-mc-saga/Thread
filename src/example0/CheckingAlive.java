package example0;

/**
 *
 * @author tadaki
 */
public class CheckingAlive {

    private int n;
    final private ThreadGroup threadGroup;

    public CheckingAlive(int n) {
        this.n = n;
        threadGroup = new ThreadGroup("threadGroup");
    }

    public void start() {
        //create n thread in threadGroup
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(threadGroup, 
                    new Thread(new ExampleRunnable(i)));
            t.start();
        }
        running();
    }

    private void running() {
        //monitoring running threads
        while ((n = threadGroup.activeCount()) > 0) {
            System.out.println(n + " threads are running");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 3;
        CheckingAlive sys = new CheckingAlive(n);
        sys.start();
    }
}
