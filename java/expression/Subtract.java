package expression;

import expression.types.Value;

public class Subtract<E extends Value<E>> extends BinaryOperation<E> {
    public Subtract(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b) {
        return a.sub(b);
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
