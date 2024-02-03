package example0;

/**
 * Run Example class through thread
 *
 * @author tadaki
 */
public class ExampleWithThread {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread thread0 = new Thread(new Runnable() {
            Example s = new Example(1);

            @Override
            public void run() {
                while (s.isRunning()) {
                    s.update();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        thread0.start();
    }

}
