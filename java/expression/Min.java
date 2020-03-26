package expression;

import expression.types.Calculator;

public class Min<E extends Number> extends BinaryOperation<E> {
    public Min(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b, Calculator<E> op) {
        return op.min(a, b);
    }

    @Override
    protected String getOperation() {
        return "min";
    }
}