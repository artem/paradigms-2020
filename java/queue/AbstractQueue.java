package queue;

import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
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

    /*
     * Pre: pred != null
     * Post: queue' = for el in queue: el != pred
     *       size' <= size
     *       order - immutable
     */
    public void removeIf(Predicate<Object> pred) {
        int length = size();
        for (int i = 0; i < length; i++) {
            Object cur = dequeue();
            if (!pred.test(cur)) {
                enqueue(cur);
            }
        }
    }

    /*
     * Pre: pred != null
     * Post: queue' = for el in queue: el == pred
     *       size' <= size
     *       order - immutable
     */
    public void retainIf(Predicate<Object> pred) {
        removeIf(pred.negate());
    }

    /*
     * Pre: pred != null
     * Post: queue' = queue if for el in queue: el == pred
     *       else queue' = {q0 .. qk}, q0..k == pred, q(k + 1) != pred
     *       size' <= size
     *       order - immutable
     */
    public void takeWhile(Predicate<Object> pred) {
        int length = size();
        int i = 0;
        for (; i < length && pred.test(element()); i++) {
            enqueue(dequeue());
        }
        for (; i < length; i++) {
            dequeue();
        }
    }

    /*
     * Pre: pred != null
     * Post: queue' = queue if for el in queue: el != pred
     *       else queue' = {qk .. qn}, q0..(k - 1) == pred, q(k + 1) != pred
     *       size' <= size
     *       order - immutable
     */
    public void dropWhile(Predicate<Object> pred) {
        int length = size();
        for (int i = 0; i < length && pred.test(element()); i++) {
            dequeue();
        }
    }
}
