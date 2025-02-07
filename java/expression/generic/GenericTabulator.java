package expression.generic;

import expression.types.*;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static final Map<String, Calculator<? extends Number>> CALCULATOR_MAP = Map.of(
            "i", new CheckedIntCalculator(),
            "d", new DoubleCalculator(),
            "bi", new BigIntCalculator(),
            "u", new IntCalculator(),
            "l", new LongCalculator(),
            "s", new ShortCalculator()
    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return new SpecialTabulator().tabulate(expression, x1, x2, y1, y2, z1, z2, CALCULATOR_MAP.get(mode));
    }
}
