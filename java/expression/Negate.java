package expression;

public class Negate extends UnaryOperation {
    public Negate(CommonExpression arg) {
        super(arg);
    }

    @Override
    protected int calculate(int a) {
        return -a;
    }

    @Override
    protected double calculate(double a) {
        return -a;
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
