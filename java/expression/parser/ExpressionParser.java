package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.exceptions.IllegalArgumentException;

import java.util.List;

public class ExpressionParser implements expression.exceptions.Parser {
    @Override
    public CommonExpression parse(final String source) throws ParserException {
        return parse(new StringSource(source));
    }

    private CommonExpression parse(StringSource expression) throws ParserException {
        return new InternalParser(expression).parse();
    }

    private static class InternalParser extends BaseParser {
        protected InternalParser(ExpressionSource source) {
            super(source);
        }

        public CommonExpression parse() throws ParserException {
            CommonExpression ret = parseOperand();
            expect('\0');

            return ret;
        }

        private CommonExpression parseOperand() throws ParserException {
            return parseAddSub();
        }

        /*
        private CommonExpression parseBitwiseShift() throws ParserException {
            CommonExpression left = parseAddSub();

            while (true) {
                skipWhitespace();
                if (test('<')) {
                    expect('<');
                    left = new LeftShift(left, parseAddSub());
                } else if (test('>')) {
                    expect('>');
                    left = new RightShift(left, parseAddSub());
                } else {
                    return left;
                }
            }
        }
         */

        private CommonExpression parseAddSub() throws ParserException {
            CommonExpression left = parseMulDiv();

            while (true) {
                skipWhitespace();
                if (test('+')) {
                    left = new CheckedAdd(left, parseMulDiv());
                } else if (test('-')) {
                    left = new CheckedSubtract(left, parseMulDiv());
                } else {
                    return left;
                }
            }
        }

        private CommonExpression parseMulDiv() throws ParserException {
            CommonExpression left = parseValue();

            while (true) {
                skipWhitespace();
                if (test('*')) {
                    left = new CheckedMultiply(left, parseValue());
                } else if (test('/')) {
                    left = new CheckedDivide(left, parseValue());
                } else {
                    return left;
                }
            }
        }

        private CommonExpression parseValue() throws ParserException{
            skipWhitespace();

            if (test('(')) {
                CommonExpression tmp = parseOperand();
                expect(')');
                return tmp;
            } else if (test('-')) {
                if (between('0', '9')) {
                    return parseNumber(false);
                } else {
                    return new Negate(parseValue());
                }
            } else if (between('0', '9')) {
                return parseNumber(true);
            } else {
                // Что-то про Set<Character> и "xyz".indexOf() . . .
                for (char var : List.of('x', 'y', 'z')) {
                    if (test(var)) {
                        return new Variable(String.valueOf(var));
                    }
                }

                ParserException e = error("Invalid variable name");
                throw new IllegalArgumentException(e.getMessage(), e.getPrefix(), e.getPos());
            }
        }
    }
}
