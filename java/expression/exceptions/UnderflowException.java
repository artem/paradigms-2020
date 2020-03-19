package expression.exceptions;

import expression.types.Value;

public class UnderflowException extends EvaluateException {
    public UnderflowException(Value<?> first) {
        super(first.getClass().getName() + " underflow", "" + first.getVal());
    }
}
