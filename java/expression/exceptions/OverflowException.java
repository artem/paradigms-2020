package expression.exceptions;

import expression.Expression;

public class OverflowException extends EvaluateException {
    public OverflowException(Expression expr) {
        super(expr.getClass().getName() + " overflow", expr);
    }
}
