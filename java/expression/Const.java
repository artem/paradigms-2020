package expression;

import expression.types.Calculator;

public final class Const<E> implements Expression<E> {
    private final E value;

    public Const(E value) {
        this.value = value;
    }

    @Override
    public E evaluate(E x, E y, E z, Calculator<E> op) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
