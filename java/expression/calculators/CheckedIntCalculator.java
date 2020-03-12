package expression.calculators;

import expression.types.CheckedInt;
import expression.types.Value;

public class CheckedIntCalculator extends AbstractCalculator {
    @Override
    public Value<CheckedInt> parse(String s) {
        return new CheckedInt(s);
    }
}
