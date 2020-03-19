package expression.exceptions;

public class DBZException extends EvaluateException {
    public DBZException(Number first, Number second) {
        super("Division by zero", first + " and " + second);
    }
}
