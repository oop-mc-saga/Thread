package example0;

import java.util.Date;

/**
 * Simple non-runnable class
 * @author tadaki
 */
public class Sample {

    protected volatile boolean running = true;
    protected int c = 0;
    private final int id;

    public Sample(int id) {
        this.id = id;
    }

    public void update() {
        Date date = new Date();
        System.out.println(id + ":" + c + " " + date.toString());
        c++;
        if (c > 10) {//Stop after 10 updates
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }

}
