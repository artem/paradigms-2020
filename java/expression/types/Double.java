package expression.types;

import static java.lang.Double.parseDouble;

public class Double extends Value<Double> {
    public Double(String s) {
        this(parseDouble(s));
    }

    public Double(double num) {
        super(num);
    }

    @Override
    public Double min(Double second) {
        double a = (double) this.val;
        double b = (double) second.val;

        return new Double(Math.min(a, b));
    }

    @Override
    public Double max(Double second) {
        double a = (double) this.val;
        double b = (double) second.val;

        return new Double(Math.max(a, b));
    }

    public Double add(Double second) {
        double a = (double) this.val;
        double b = (double) second.val;

        return new Double(a + b);
    }

    public Double sub(Double second) {
        double a = (double) this.val;
        double b = (double) second.val;

        return new Double(a - b);
    }

    public Double mul(Double second) {
        double a = (double) this.val;
        double b = (double) second.val;

        return new Double(a * b);
    }

    public Double div(Double second) {
        double a = (double) this.val;
        double b = (double) second.val;

        return new Double(a / b);
    }

    public Double negate() {
        return new Double(-((double) this.val));
    }

    @Override
    public Double count() {
        long bits = java.lang.Double.doubleToLongBits((java.lang.Double) this.val);
        return new Double(java.lang.Long.bitCount(bits));
    }
}
