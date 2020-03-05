package queue;

/*
 * INV: n >= 0
 *      for i 0..n-1 : queue[i] != null
 */
public class LinkedQueue extends AbstractQueue implements ArrayQueueTest.Queue {
    private Node head;
    private Node tail;
    @Override
    public void enqueue(Object element) {
        head = new Node(element, head);
        if (tail == null) {
            tail = head;
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
        private final Node next;

        private Node(Object val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
}
