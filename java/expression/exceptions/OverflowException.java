package expression.exceptions;

public class OverflowException extends EvaluateException {
    public OverflowException(Number first, Number second) {
        super(first.getClass().getName() + " overflow", first + " and " + second);
    }
}
