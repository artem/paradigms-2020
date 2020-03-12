package expression.types;

import static java.lang.Double.parseDouble;

public class Double extends Value<Double> {
    public Double(String s) {
        this(parseDouble(s));
    }

    public Double(double num) {
        super(num);
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
}
