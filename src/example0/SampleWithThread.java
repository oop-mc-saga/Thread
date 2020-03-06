package example0;

/**
 * SampleをRunnableに拡張せずにthreadとして実行
 * @author tadaki
 */
public class SampleWithThread {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread thread0 = new Thread(new Runnable() {
            Sample s = new Sample(1);

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
