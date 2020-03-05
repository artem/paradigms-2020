package queue;

public abstract class AbstractQueue {
    protected int size = 0;

    /*
     * Post: R = |queue|
     *           queue - immutable
     */
    public int size() {
        return size;
    }

    /*
     * Post: R = |queue| == 0
     *           queue - immutable
     */
    public boolean isEmpty() {
        return size == 0;
    }
}
