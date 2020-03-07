package expression.exceptions;

import expression.CommonExpression;

public class OverflowException extends EvaluateException {
    public OverflowException(CommonExpression expr) {
        super(expr.getClass().getName() + " overflow", expr);
    }
}
