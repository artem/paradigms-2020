package expression.exceptions;

public class MissingParenthesisException extends ParserException {
    public MissingParenthesisException(String message, String prefix, int pos) {
        super(message, prefix, pos);
    }
}
