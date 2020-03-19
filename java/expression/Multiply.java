package expression;

import expression.types.Calculator;

public class Multiply<E> extends BinaryOperation<E> {
    public Multiply(Expression<E> arg1, Expression<E> arg2) {
        super(arg1, arg2);
    }

    @Override
    protected E calculate(E a, E b, Calculator<E> op) {
        return op.mul(a, b);
    }

    @Override
    protected String getOperation() {
        return "*";
    }
}
