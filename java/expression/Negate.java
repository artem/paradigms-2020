package expression;

import expression.types.Value;

public class Negate<E extends Value<E>> extends UnaryOperation<E> {
    public Negate(Expression<E> arg) {
        super(arg);
    }

    @Override
    protected E calculate(E a) {
        return a.negate();
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
