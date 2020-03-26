package expression.generic;

import expression.Expression;
import expression.exceptions.EvaluateException;
import expression.exceptions.ParserException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.types.Calculator;

public class SpecialTabulator {
    public <T extends Number> Object[][][] tabulate(String expression, int x1, int x2, int y1, int y2, int z1, int z2, Calculator<T> calc) throws ParserException {
        int xDelta = x2 - x1 + 1;
        int yDelta = y2 - y1 + 1;
        int zDelta = z2 - z1 + 1;
        Object[][][] table = new Object[xDelta][yDelta][zDelta];

        Parser<T> parser = new ExpressionParser<>();
        Expression<T> expr = parser.parse(expression, calc);
        for (int i = 0; i < xDelta; i++) {
            for (int j = 0; j < yDelta; j++) {
                for (int k = 0; k < zDelta; k++) {
                    try {
                        table[i][j][k] = expr.evaluate(calc.cast(x1 + i), calc.cast(y1 + j), calc.cast(z1 + k), calc);
                    } catch (EvaluateException e) {
                        // ignore
                    }
                }
            }
        }

        return table;
    }
}
