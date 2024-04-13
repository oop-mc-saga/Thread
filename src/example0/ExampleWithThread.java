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
            //create an instance of Example
            Example s = new Example(1);

            @Override
            public void run() {
                while (s.isRunning()) {
                    s.update();//update the state
                    try {
                        Thread.sleep(1000);//wait for 1 second
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        thread0.start();
    }

}
