package queue;

public class ArrayQueueModule {
    private static int INIT_CAPACITY = 4;
    private static Object[] queue = new Object[INIT_CAPACITY];
    private static int size = 0;
    private static int start = 0;
    private static int end = 0; // start + size по модулю

    /*
     * Pre: e != null
     * Post: queue' = {queue[0] .. queue[n - 1], e}
     *       |queue'| = |queue| + 1
     */
    public static void enqueue(Object e) {
        if (size == queue.length) {
            Object[] old = queue;
            queue = new Object[queue.length * 2];
            if (end <= start) {
                System.arraycopy(old, start, queue, 0, old.length - start);
                System.arraycopy(old, 0, queue, old.length - start, end);
            } else {
                System.arraycopy(old, start, queue, 0, end - start);
            }
            start = 0;
            end = size;
        }

        queue[end] = e;
        end = (end + 1) % queue.length;
        size++;
    }

    /*
     * Pre: |queue| >= 1
     * Post: queue - immutable
     */
    public static Object element() {
        return queue[start];
    }

    /*
     * Pre: |queue| >= 1
     * Post: R = queue[0]
     *       queue' = {queue[1] .. queue[n - 1]}
     *       |queue'| = |queue| - 1
     */
    public static Object dequeue() {
        Object ret = queue[start];
        queue[start] = null;
        start = (start + 1) % queue.length;
        size--;
        return ret;
    }

    /*
     * Post: R = |queue|
     *           queue - immutable
     */
    public static int size() {
        return size;
    }

    /*
     * Post: R = |queue| == 0
     *           queue - immutable
     */
    public static boolean isEmpty() {
        return size == 0;
    }

    /*
     * Post: |queue| = 0
     *       queue - immutable
     */
    public static void clear() {
        queue = new Object[INIT_CAPACITY];
        start = 0;
        end = 0;
        size = 0;
    }
}
