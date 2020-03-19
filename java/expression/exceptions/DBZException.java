package expression.exceptions;

import expression.types.Value;

public class DBZException extends EvaluateException {
    public DBZException(Value<?> first, Value<?> second) {
        super("Division by zero", first.getVal() + " and " + second.getVal());
    }
}
