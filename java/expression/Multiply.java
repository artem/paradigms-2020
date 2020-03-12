package expression;

import expression.types.Value;

public class Multiply<E extends Value<E>> extends BinaryOperation<E> {
    public Multiply(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b) {
        return a.mul(b);
    }

    @Override
    protected String getOperation() {
        return "*";
    }
}
