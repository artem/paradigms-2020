package expression;

import expression.types.Value;

public class Add<E extends Value<E>> extends BinaryOperation<E> {
    public Add(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b) {
        return a.add(b);
    }

    @Override
    protected String getOperation() {
        return "+";
    }
}
