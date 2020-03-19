package expression;

import expression.types.Calculator;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            Const second = (Const) obj;
            return Objects.equals(value, second.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
