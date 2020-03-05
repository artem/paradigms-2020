package queue;

public interface Queue {
    /*
     * Pre: e != null
     * Post: queue' = {queue[0] .. queue[n - 1], e}
     *       |queue'| = |queue| + 1
     */
    void enqueue(Object element);

    /*
     * Pre: |queue| >= 1
     * Post: queue - immutable
     */
    Object element();

    /*
     * Pre: |queue| >= 1
     * Post: R = queue[0]
     *       queue' = {queue[1] .. queue[n - 1]}
     *       |queue'| = |queue| - 1
     */
    Object dequeue();

    /*
     * Post: R = |queue|
     *           queue - immutable
     */
    int size();

    /*
     * Post: R = |queue| == 0
     *           queue - immutable
     */
    boolean isEmpty();

    /*
     * Post: |queue| = 0
     */
    void clear();

    /*
     * Post: queue - immutable
     *       R = "[q0, q1, .. qn]"
     */
    String toStr();

    Object[] toArray();
}
