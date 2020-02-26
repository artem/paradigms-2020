package queue;

public class ArrayQueueADT {
    private Object[] queue = new Object[4];
    private int size = 0;
    private int start = 0;
    private int end = 0; // start + size по модулю

    public static void enqueue(ArrayQueueADT q, Object e) {
        if (q.size == q.queue.length) {
            Object[] old = q.queue;
            q.queue = new Object[q.queue.length * 2];
            if (q.end <= q.start) {
                System.arraycopy(old, q.start, q.queue, 0, old.length - q.start);
                System.arraycopy(old, 0, q.queue, old.length - q.start, q.end);
            } else {
                System.arraycopy(old, q.start, q.queue, 0, q.end - q.start);
            }
            q.start = 0;
            q.end = q.size;
        }

        q.queue[q.end] = e;
        q.end = (q.end + 1) % q.queue.length;
        q.size++;
    }

    public static Object element(ArrayQueueADT q) {
        return q.queue[q.start];
    }

    public static void clear(ArrayQueueADT q) {
        q.queue = new Object[4];
        q.start = 0;
        q.end = 0;
        q.size = 0;
    }

    public static Object dequeue(ArrayQueueADT q) {
        Object ret = q.queue[q.start];
        q.queue[q.start] = null;
        q.start = (q.start + 1) % q.queue.length;
        q.size--;
        return ret;
    }

    public static boolean isEmpty(ArrayQueueADT q) {
        return q.size == 0;
    }

    public static int size(ArrayQueueADT q) {
        return q.size;
    }
}
