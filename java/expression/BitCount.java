package expression;

import expression.types.Value;

public class BitCount<E extends Value<E>> extends UnaryOperation<E> {
    public BitCount(Expression<E> arg) {
        super(arg);
    }

    @Override
    protected E calculate(E a) {
        return a.count();
    }

    @Override
    protected String getOperation() {
        return "count";
    }
}
