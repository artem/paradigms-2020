package queue;

/*
 * INV: n >= 0
 *      for i 0..n-1 : queue[i] != null
 */
public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    /*
     * Pre: e != null
     * Post: queue' = {queue[0] .. queue[n - 1], e}
     *       |queue'| = |queue| + 1
     */
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

    /*
     * Pre: |queue| >= 1
     * Post: queue - immutable
     */
    @Override
    public Object element() {
        return head.val;
    }

    /*
     * Pre: |queue| >= 1
     * Post: R = queue[0]
     *       queue' = {queue[1] .. queue[n - 1]}
     *       |queue'| = |queue| - 1
     */
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

    /*
     * Post: |queue| = 0
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /*
     * Post: queue - immutable
     *       R = [q0, q1, .. qn]
     */
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

    /*
     * Post: queue- immutable
     *       R = a: a = {q0, q1, .. qn}
     */
    @Override
    public Object[] toArray() {
        Object[] ret = new Object[size];
        Node cur = head;
        for (int i = 0; i < size; i++) {
            ret[i] = cur.val;
            cur = cur.next;
        }
        return ret;
    }

    private static class Node {
        private final Object val;
        private Node next;

        private Node(Object val) {
            this.val = val;
        }
    }
}
