package expression.exceptions;

import expression.CommonExpression;

public class UnderflowException extends EvaluateException {
    public UnderflowException(CommonExpression expr) {
        super(expr.getClass().getName() + " underflow", expr);
    }
}
