package expression.exceptions;

import expression.Expression;

public class DBZException extends EvaluateException {
    public DBZException(Expression expr) {
        super("Division by zero", expr);
    }
}
