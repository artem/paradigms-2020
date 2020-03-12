package expression.exceptions;

import expression.Expression;

public abstract class EvaluateException extends RuntimeException {
    public EvaluateException(String err, Expression expr) {
        super(err + ": " + expr);
    }
}
