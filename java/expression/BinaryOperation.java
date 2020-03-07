package expression;

import java.util.Objects;

public abstract class BinaryOperation extends Operation {
    private final CommonExpression arg1;
    private final CommonExpression arg2;

    protected BinaryOperation(CommonExpression arg1, CommonExpression arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public String toString() {
        return "(" + arg1 + " " + getOperation() + " " + arg2 + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            BinaryOperation second = (BinaryOperation) obj;
            return Objects.equals(arg1, second.arg1) && Objects.equals(arg2, second.arg2);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, getClass());
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(arg1.evaluate(x, y, z), arg2.evaluate(x, y, z));
    }

    @Override
    public double evaluate(double x) {
        return calculate(arg1.evaluate(x), arg2.evaluate(x));
    }

    protected abstract double calculate(double a, double b);
    protected abstract int calculate(int a, int b);
}
