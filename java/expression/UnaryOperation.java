package expression;

import java.util.Objects;

public abstract class UnaryOperation<E> extends Operation<E> {
    private final Expression<E> arg1;

    protected UnaryOperation(Expression<E> arg1) {
        this.arg1 = arg1;
    }

    @Override
    public String toString() {
        return getOperation() + arg1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            UnaryOperation second = (UnaryOperation) obj;
            return Objects.equals(arg1, second.arg1);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(arg1) * 31 + Objects.hashCode(getClass());
    }

    @Override
    public E evaluate(E x, E y, E z) {
        return calculate(arg1.evaluate(x, y, z));
    }

    protected abstract E calculate(E a);
}
