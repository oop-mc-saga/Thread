package example0;

/**
 *
 * @author tadaki
 */
public class CheckingAlive {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 3;
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");

        //create n thread in threadGroup
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(threadGroup, new Thread(new ExampleRunnable(i)));
            t.start();
        }

        //monitoring running threads
        while ((n = threadGroup.activeCount()) > 0) {
            System.out.println(n + " threads are running");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}
