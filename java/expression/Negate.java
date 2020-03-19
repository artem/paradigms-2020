package expression;

import expression.types.Calculator;

public class Negate<E> extends UnaryOperation<E> {
    public Negate(Expression<E> arg) {
        super(arg);
    }

    @Override
    protected E calculate(E a, Calculator<E> op) {
        return op.negate(a);
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
