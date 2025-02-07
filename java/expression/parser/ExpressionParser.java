package expression.parser;

import expression.*;
import expression.exceptions.IllegalArgumentException;
import expression.exceptions.ParserException;
import expression.types.Calculator;

import java.util.List;

public class ExpressionParser<E extends Number> implements Parser<E> {
    @Override
    public Expression<E> parse(final String source, Calculator<E> op) throws ParserException {
        return parse(new StringSource(source), op);
    }

    private Expression<E> parse(StringSource expression, Calculator<E> op) throws ParserException {
        return new InternalParser(expression, op).parse();
    }

    private class InternalParser extends BaseParser<E> {
        protected InternalParser(ExpressionSource source, Calculator<E> op) {
            super(source, op);
        }

        public Expression<E> parse() throws ParserException {
            Expression<E> ret = parseOperand();
            expect('\0');

            return ret;
        }

        private Expression<E> parseOperand() throws ParserException {
            return parseMinMax();
        }

        private Expression<E> parseMinMax() throws ParserException {
            Expression<E> left = parseAddSub();

            while (true) {
                skipWhitespace();
                if (test('m')) {
                    if (test('i')) {
                        expect('n');
                        left = new Min<E>(left, parseAddSub());
                    } else if (test('a')) {
                        expect('x');
                        left = new Max<>(left, parseAddSub());
                    } else {
                        throw error("Starts with m");
                    }
                } else {
                    return left;
                }
            }
        }

        private Expression<E> parseAddSub() throws ParserException {
            Expression<E> left = parseMulDiv();

            while (true) {
                skipWhitespace();
                if (test('+')) {
                    left = new Add<>(left, parseMulDiv());
                } else if (test('-')) {
                    left = new Subtract<>(left, parseMulDiv());
                } else {
                    return left;
                }
            }
        }

        private Expression<E> parseMulDiv() throws ParserException {
            Expression<E> left = parseValue();

            while (true) {
                skipWhitespace();
                if (test('*')) {
                    left = new Multiply<>(left, parseValue());
                } else if (test('/')) {
                    left = new Divide<>(left, parseValue());
                } else {
                    return left;
                }
            }
        }

        private Expression<E> parseValue() throws ParserException{
            skipWhitespace();

            if (test('(')) {
                Expression<E> tmp = parseOperand();
                expect(')');
                return tmp;
            } else if (test('-')) {
                if (between('0', '9')) {
                    return parseNumber(false);
                } else {
                    return new Negate<>(parseValue());
                }
            } else if (test('c')) {
                expect("ount");
                return new BitCount<>(parseValue());
            } else if (between('0', '9')) {
                return parseNumber(true);
            } else {
                for (char var : List.of('x', 'y', 'z')) {
                    if (test(var)) {
                        return new Variable<E>(String.valueOf(var));
                    }
                }

                ParserException e = error("Invalid variable name");
                throw new IllegalArgumentException(e.getMessage(), e.getPrefix(), e.getPos());
            }
        }
    }
}
