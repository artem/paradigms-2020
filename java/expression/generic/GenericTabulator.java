package expression.generic;

import expression.calculators.BigIntCalculator;
import expression.calculators.CheckedIntCalculator;
import expression.calculators.DoubleCalculator;
import expression.types.BigInt;
import expression.types.CheckedInt;
import expression.types.Double;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        switch (mode) {
            case "i":
                return new SpecialTabulator<CheckedInt>().tabulate(expression, x1, x2, y1, y2, z1, z2, new CheckedIntCalculator());
            case "d":
                return new SpecialTabulator<Double>().tabulate(expression, x1, x2, y1, y2, z1, z2, new DoubleCalculator());
            case "bi":
                return new SpecialTabulator<BigInt>().tabulate(expression, x1, x2, y1, y2, z1, z2, new BigIntCalculator());
            default:
                throw new IllegalArgumentException("oof");//fixme
        }
    }
}
