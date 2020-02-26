package queue;

public class ArrayQueue {
    private Object[] queue = new Object[4];
    private int size = 0;
    private int start = 0;
    private int end = 0; // start + size по модулю

    public void enqueue(Object e) {
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

    public Object element() {
        return queue[start];
    }

    public void clear() {
        queue = new Object[4];
        start = 0;
        end = 0;
        size = 0;
    }

    public Object dequeue() {
        Object ret = queue[start];
        queue[start] = null;
        start = (start + 1) % queue.length;
        size--;
        return ret;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
