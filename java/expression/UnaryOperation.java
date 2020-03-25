package expression;

import expression.types.Calculator;

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
    public E evaluate(E x, E y, E z, Calculator<E> op) {
        return calculate(arg1.evaluate(x, y, z, op), op);
    }

    protected abstract E calculate(E a, Calculator<E> op);
}
