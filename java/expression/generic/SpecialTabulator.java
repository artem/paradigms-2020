package expression.generic;

import expression.Expression;
import expression.calculators.AbstractCalculator;
import expression.exceptions.EvaluateException;
import expression.exceptions.ParserException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.types.Value;

public class SpecialTabulator<T extends Value<T>> {
    public Number[][][] tabulate(String expression, int x1, int x2, int y1, int y2, int z1, int z2, AbstractCalculator calc) throws ParserException {
        int xDelta = x2 - x1 + 1;
        int yDelta = y2 - y1 + 1;
        int zDelta = z2 - z1 + 1;
        Number[][][] table = new Number[xDelta][yDelta][zDelta];

        Parser<T> parser = new ExpressionParser<T>(calc);
        Expression<T> expr = parser.parse(expression);
        for (int i = 0; i < xDelta; i++) {
            for (int j = 0; j < yDelta; j++) {
                for (int k = 0; k < zDelta; k++) {
                    try {
                        table[i][j][k] = expr.evaluate((T) calc.cast(x1 + i), (T) calc.cast(y1 + j), (T) calc.cast(z1 + k)).getVal();
                    } catch (Exception e) { // EvaluateException FIXME
                        // Keep null
                    }
                }
            }
        }

        return table;
    }
}
