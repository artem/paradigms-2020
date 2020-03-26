package expression;

import expression.types.Calculator;

public abstract class BinaryOperation<E extends Number> extends Operation<E> {
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
    public E evaluate(E x, E y, E z, Calculator<E> op) {
        return calculate(arg1.evaluate(x, y, z, op), arg2.evaluate(x, y, z, op), op);
    }

    protected abstract E calculate(E a, E b, Calculator<E> op);
}
