package expression.exceptions;

import expression.Expression;

public class CalculateException extends EvaluateException {
    public CalculateException(String err, Expression expr) {
        super(expr.getClass().getName() + ": " + err, expr);
    }
}
