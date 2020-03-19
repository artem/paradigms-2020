package expression.parser;

import expression.Expression;
import expression.exceptions.ParserException;
import expression.types.Calculator;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser<E> {
    Expression<E> parse(String expression, Calculator<E> op) throws ParserException;
}
