package expression.exceptions;

import expression.Expression;

public class UnderflowException extends EvaluateException {
    public UnderflowException(Expression expr) {
        super(expr.getClass().getName() + " underflow", expr);
    }
}
