package expression.exceptions;

import expression.types.Value;

public class OverflowException extends EvaluateException {
    public OverflowException(Value<?> first, Value<?> second) {
        super(first.getClass().getName() + " overflow", first.getVal() + " and " + second.getVal());
    }
}
