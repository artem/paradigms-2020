package expression.exceptions;

public class UnderflowException extends EvaluateException {
    public UnderflowException(Number first) {
        super(first.getClass().getName() + " underflow", "" + first);
    }
}
