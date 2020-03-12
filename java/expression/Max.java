package expression;

import expression.types.Value;

public class Max<E extends Value<E>> extends BinaryOperation<E> {
    public Max(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b) {
        return a.max(b);
    }

    @Override
    protected String getOperation() {
        return "max";
    }
}