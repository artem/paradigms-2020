package queue;

import java.util.function.Predicate;

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

    /*
     * Post: queue - immutable
     *       R = a: a = {q0, q1, .. qn}
     */
    Object[] toArray();

    /*
     * Pre: pred != null
     * Post: queue' = for el in queue: el != pred
     *       size' <= size
     *       order - immutable
     */
    void removeIf(Predicate<Object> pred);

    /*
     * Pre: pred != null
     * Post: queue' = for el in queue: el == pred
     *       size' <= size
     *       order - immutable
     */
    void retainIf(Predicate<Object> pred);

    /*
     * Pre: pred != null
     * Post: queue' = queue if for el in queue: el == pred
     *       else queue' = {q0 .. qk}, q0..k == pred, q(k + 1) != pred
     *       size' <= size
     *       order - immutable
     */
    void takeWhile(Predicate<Object> pred);

    /*
     * Pre: pred != null
     * Post: queue' = queue if for el in queue: el != pred
     *       else queue' = {qk .. qn}, q0..(k - 1) == pred, q(k + 1) != pred
     *       size' <= size
     *       order - immutable
     */
    void dropWhile(Predicate<Object> pred);
}
