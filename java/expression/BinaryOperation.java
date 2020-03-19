package expression;

import expression.types.Calculator;

import java.util.Objects;

public abstract class BinaryOperation<E> extends Operation<E> {
    private final Expression<E> arg1;
    private final Expression<E> arg2;

    protected BinaryOperation(Expression<E> arg1, Expression<E> arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public String toString() {
        return "(" + arg1 + " " + getOperation() + " " + arg2 + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            BinaryOperation second = (BinaryOperation) obj;
            return Objects.equals(arg1, second.arg1) && Objects.equals(arg2, second.arg2);
        } else {
            return false;
        }
    }

    @Override
    public E evaluate(E x, E y, E z, Calculator<E> op) {
        return calculate(arg1.evaluate(x, y, z, op), arg2.evaluate(x, y, z, op), op);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, getClass());
    }

    protected abstract E calculate(E a, E b, Calculator<E> op);
}
