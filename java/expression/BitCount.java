package expression;

import expression.types.Calculator;

public class BitCount<E extends Number> extends UnaryOperation<E> {
    public BitCount(Expression<E> arg) {
        super(arg);
    }

    @Override
    protected E calculate(E a, Calculator<E> op) {
        return op.count(a);
    }

    @Override
    protected String getOperation() {
        return "count";
    }
}
