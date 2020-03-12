package expression.parser;

import expression.Expression;
import expression.exceptions.ParserException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser<E> {
    Expression<E> parse(String expression) throws ParserException;
}
