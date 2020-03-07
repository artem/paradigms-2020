package expression;

import java.util.Objects;

public abstract class UnaryOperation extends Operation {
    private final CommonExpression arg1;

    protected UnaryOperation(CommonExpression arg1) {
        this.arg1 = arg1;
    }

    @Override
    public String toString() {
        return getOperation() + arg1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            UnaryOperation second = (UnaryOperation) obj;
            return Objects.equals(arg1, second.arg1);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(arg1) * 31 + Objects.hashCode(getClass());
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(arg1.evaluate(x, y, z));
    }

    @Override
    public double evaluate(double x) {
        return calculate(arg1.evaluate(x));
    }

    protected abstract double calculate(double a);
    protected abstract int calculate(int a);
}
