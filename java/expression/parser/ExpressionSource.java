package expression.parser;

import expression.exceptions.ParserException;

public interface ExpressionSource {
    boolean hasNext();
    char next();
    ParserException error(final String message);
}
