package expression.types;

public interface Calculator<E> {
    E min(E val1, E val2);
    E max(E val1, E val2);
    E add(E val1, E val2);
    E sub(E val1, E val2);
    E mul(E val1, E val2);
    E div(E val1, E val2);
    E negate(E val);
    E count(E val);
    E parse(String val);
    E cast(int val);
}
