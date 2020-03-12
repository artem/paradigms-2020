package expression.calculators;

import expression.types.Double;
import expression.types.Value;

public class DoubleCalculator extends AbstractCalculator {
    @Override
    public Value<Double> parse(String s) {
        return new Double(s);
    }
}
