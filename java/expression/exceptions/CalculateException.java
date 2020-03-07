package expression.exceptions;

import expression.CommonExpression;

public class CalculateException extends EvaluateException {
    public CalculateException(String err, CommonExpression expr) {
        super(expr.getClass().getName() + ": " + err, expr);
    }
}
