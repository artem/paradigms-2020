package expression;

import expression.types.Value;

public class Divide<E extends Value<E>> extends BinaryOperation<E> {
    public Divide(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b) {
        return a.div(b);
    }

    @Override
    protected String getOperation() {
        return "/";
    }
}
