package expression.exceptions;

public class ParserException extends Exception {
    private final int pos;
    private final String prefix;

    public ParserException(String message, String prefix, int pos) {
        super("Symbol " + pos + ": " + prefix + " <-... " + message);
        this.pos = pos;
        this.prefix = prefix;
    }

    public int getPos() {
        return pos;
    }

    public String getPrefix() {
        return prefix;
    }
}
