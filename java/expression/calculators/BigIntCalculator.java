package expression.calculators;

import expression.types.BigInt;
import expression.types.Value;

public class BigIntCalculator extends AbstractCalculator {
    @Override
    public Value<BigInt> parse(String s) {
        return new BigInt(s);
    }
}
