package expression.types;

public class DoubleCalculator implements Calculator<Double> {
    @Override
    public Double min(Double val1, Double val2) {
        return Math.min(val1, val2);
    }

    @Override
    public Double max(Double val1, Double val2) {
        return Math.max(val1, val2);
    }

    @Override
    public Double add(Double val1, Double val2) {
        return val1 + val2;
    }

    @Override
    public Double sub(Double val1, Double val2) {
        return val1 - val2;
    }

    @Override
    public Double mul(Double val1, Double val2) {
        return val1 * val2;
    }

    @Override
    public Double div(Double val1, Double val2) {
        return val1 / val2;
    }

    @Override
    public Double negate(Double val) {
        return -val;
    }

    @Override
    public Double count(Double val) {
        long bits = Double.doubleToLongBits(val);
        return (double) Long.bitCount(bits);
    }

    @Override
    public Double parse(String val) {
        return Double.parseDouble(val);
    }

    @Override
    public Double cast(int val) {
        return (double) val;
    }
}
