package queue;

/*
 * INV: n >= 0
 *      for i 0..n-1 : queue[i] != null
 */
public class ArrayQueueADT {
    private static int INIT_CAPACITY = 4;
    private Object[] queue = new Object[INIT_CAPACITY];
    private int size = 0;
    private int start = 0;

    /*
     * Pre: e != null
     * Post: queue' = {queue[0] .. queue[n - 1], e}
     *       |queue'| = |queue| + 1
     */
    public static void enqueue(ArrayQueueADT q, Object e) {
        if (q.size == q.queue.length) {
            Object[] old = q.queue;
            int end = (q.start + q.size) % old.length;
            q.queue = new Object[q.queue.length * 2];

            if (end <= q.start) {
                System.arraycopy(old, q.start, q.queue, 0, old.length - q.start);
                System.arraycopy(old, 0, q.queue, old.length - q.start, end);
            } else {
                System.arraycopy(old, q.start, q.queue, 0, end - q.start);
            }
            q.start = 0;
        }

        q.queue[(q.start + q.size) % q.queue.length] = e;
        q.size++;
    }

    /*
     * Pre: |queue| >= 1
     * Post: queue - immutable
     */
    public static Object element(ArrayQueueADT q) {
        return q.queue[q.start];
    }

    /*
     * Pre: |queue| >= 1
     * Post: R = queue[0]
     *       queue' = {queue[1] .. queue[n - 1]}
     *       |queue'| = |queue| - 1
     */
    public static Object dequeue(ArrayQueueADT q) {
        Object ret = q.queue[q.start];
        q.queue[q.start] = null;
        q.start = (q.start + 1) % q.queue.length;
        q.size--;
        return ret;
    }

    /*
     * Post: R = |queue|
     *           queue - immutable
     */
    public static int size(ArrayQueueADT q) {
        return q.size;
    }

    /*
     * Post: R = |queue| == 0
     *           queue - immutable
     */
    public static boolean isEmpty(ArrayQueueADT q) {
        return q.size == 0;
    }

    /*
     * Post: |queue| = 0
     *       queue - immutable
     */
    public static void clear(ArrayQueueADT q) {
        q.queue = new Object[INIT_CAPACITY];
        q.start = 0;
        q.size = 0;
    }

    /*
     * Post: queue - immutable
     *       R = [q0, q1, .. qn]
     */
    public static String toStr(ArrayQueueADT q) {
        StringBuilder sb = new StringBuilder("[");
        if (q.size > 0) {
            sb.append(q.queue[q.start]);
            for (int i = 1; i < q.size; i++) {
                sb.append(", ");
                sb.append(q.queue[(q.start + i) % q.queue.length]);
            }
        }
        return sb.append("]").toString();
    }
}
