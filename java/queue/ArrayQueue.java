package queue;

/*
 * INV: n >= 0
 *      for i 0..n-1 : queue[i] != null
 */
public class ArrayQueue extends AbstractQueue implements Queue {
    private static int INIT_CAPACITY = 32;
    private Object[] queue = new Object[INIT_CAPACITY];
    private int start = 0;

    /*
     * Pre: e != null
     * Post: queue' = {queue[0] .. queue[n - 1], e}
     *       |queue'| = |queue| + 1
     */
    @Override
    public void enqueue(Object e) {
        if (size == queue.length) {
            Object[] old = queue;
            int end = (start + size) % old.length;
            queue = new Object[queue.length * 2];

            if (end <= start) {
                System.arraycopy(old, start, queue, 0, old.length - start);
                System.arraycopy(old, 0, queue, old.length - start, end);
            } else {
                System.arraycopy(old, start, queue, 0, end - start);
            }
            start = 0;
        }

        queue[(start + size) % queue.length] = e;
        size++;
    }

    /*
     * Pre: |queue| >= 1
     * Post: queue - immutable
     */
    @Override
    public Object element() {
        return queue[start];
    }

    /*
     * Pre: |queue| >= 1
     * Post: R = queue[0]
     *       queue' = {queue[1] .. queue[n - 1]}
     *       |queue'| = |queue| - 1
     */
    @Override
    public Object dequeue() {
        Object ret = queue[start];
        queue[start] = null;
        start = (start + 1) % queue.length;
        size--;
        return ret;
    }

    /*
     * Post: |queue| = 0
     */
    @Override
    public void clear() {
        queue = new Object[INIT_CAPACITY];
        start = 0;
        size = 0;
    }

    /*
     * Post: queue - immutable
     *       R = "[q0, q1, .. qn]"
     */
    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder("[");
        if (size > 0) {
            sb.append(queue[start]);
            for (int i = 1; i < size; i++) {
                sb.append(", ");
                sb.append(queue[(start + i) % queue.length]);
            }
        }
        return sb.append("]").toString();
    }
}
