package expression;

public interface Expression<E> {
    E evaluate(E x, E y, E z);
}
