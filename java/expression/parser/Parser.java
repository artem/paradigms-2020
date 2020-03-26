package expression.parser;

import expression.Expression;
import expression.exceptions.ParserException;
import expression.types.Calculator;

public interface Parser<E extends Number> {
    Expression<E> parse(String expression, Calculator<E> op) throws ParserException;
}
