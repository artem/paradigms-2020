package expression.calculators;

import expression.types.Value;

public abstract class AbstractCalculator {

    public Value<?> cast(Number num) {
        return parse(String.valueOf(num));
    }

    public abstract Value<?> parse(String s);
}
