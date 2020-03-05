package queue;

/*
 * INV: n >= 0
 *      for i 0..n-1 : queue[i] != null
 */
public class LinkedQueue extends AbstractQueue implements Queue {
    private Node head;
    private Node tail;

    @Override
    public void enqueue(Object element) {
        if (tail == null) {
            head = new Node(element);
            tail = head;
        } else {
            tail.next = new Node(element);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public Object element() {
        return head.val;
    }

    @Override
    public Object dequeue() {
        Object ret = element();
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return ret;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private class Node {
        private final Object val;
        private Node next;

        private Node(Object val) {
            this.val = val;
        }
    }

    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder("[");
        if (size > 0) {
            Node cur = head;
            sb.append(cur.val);
            for (int i = 1; i < size; i++) {
                sb.append(", ");
                cur = cur.next;
                sb.append(cur.val);
            }
        }
        return sb.append("]").toString();
    }
}
