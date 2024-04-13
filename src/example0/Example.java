package example0;

import java.util.Date;

/**
 * Simple non-runnable class
 * 
 * @author tadaki
 */
public class Example {

    protected volatile boolean running = true;//flag to stop the thread
    protected int c = 0;//counter
    private final int id;//
    private final int maxCount = 10;

    public Example(int id) {
        this.id = id;
    }

    /**
     * Update the state of the object
     */
    public void update() {
        Date date = new Date();
        System.out.println(id + ":" + c + " " + date.toString());
        c++;
        if (c > maxCount) {//Stop after maxCount updates
            running = false;//change the flag to stop the thread
        }
    }

    /**
     * Check if the object is running
     *
     * @return true if the object is running
     */
    public boolean isRunning() {
        return running;
    }

}
