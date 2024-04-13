package example0;

/**
 * Example class implements Runnable interface
 * This class is runnable
 * 
 * @author tadaki
 */
public class ExampleRunnable extends Example implements Runnable {

    public ExampleRunnable(int id) {
        super(id);
    }

    /**
     * update() at random timing
     */
    @Override
    public void run() {
        while (running) {
            update();//update the state
            int t = (int) (1000 * Math.random());
            try {
                Thread.sleep(t);//wait for t milliseconds
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Thread(new ExampleRunnable(1)).start();
        new Thread(new ExampleRunnable(2)).start();
        Thread t = new Thread(new ExampleRunnable(3));
        t.start();
    }

}
