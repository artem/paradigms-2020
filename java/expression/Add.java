package expression;

import expression.types.Calculator;

public class Add<E extends Number> extends BinaryOperation<E> {
    public Add(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b, Calculator<E> op) {
        return op.add(a, b);
    }

    @Override
    protected String getOperation() {
        return "+";
    }
}
