package expression.exceptions;

import expression.CommonExpression;

public class DBZException extends EvaluateException {
    public DBZException(CommonExpression expr) {
        super("Division by zero", expr);
    }
}
