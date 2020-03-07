package expression.exceptions;

import expression.CommonExpression;

public abstract class EvaluateException extends RuntimeException {
    public EvaluateException(String err, CommonExpression expr) {
        super(err + ": " + expr);
    }
}
