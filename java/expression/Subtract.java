package expression;

import expression.types.Calculator;

public class Subtract<E> extends BinaryOperation<E> {
    public Subtract(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b, Calculator<E> op) {
        return op.sub(a, b);
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
