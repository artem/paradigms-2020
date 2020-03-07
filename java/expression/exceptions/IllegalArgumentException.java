package expression.exceptions;

public class IllegalArgumentException extends ParserException {
    public IllegalArgumentException(String message, String prefix, int pos) {
        super(message, prefix, pos);
    }
}
