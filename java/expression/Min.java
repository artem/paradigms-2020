package expression;

import expression.types.Value;

public class Min<E extends Value<E>> extends BinaryOperation<E> {
    public Min(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b) {
        return a.min(b);
    }

    @Override
    protected String getOperation() {
        return "min";
    }
}