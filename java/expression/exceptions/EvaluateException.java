package expression.exceptions;

public abstract class EvaluateException extends RuntimeException {
    public EvaluateException(String err, String args) {
        super(err + ": " + args);
    }
}
