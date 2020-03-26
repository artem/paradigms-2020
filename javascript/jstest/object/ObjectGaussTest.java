package jstest.object;

import jstest.ArithmeticTests;
import jstest.Language;

import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ObjectGaussTest extends ObjectExpressionTest {
    public static final Dialect GAUSS_DIALECT = ObjectExpressionTest.ARITHMETIC_DIALECT.copy()
            .rename("gauss", "Gauss");

    public static class SquareSqrtTests extends ArithmeticTests {{
        any("gauss", 4, args -> gauss(args.get(0), args.get(1), args.get(2), args.get(3)));
        final AbstractExpression gauss1 = f("gauss", vx, vy, vz, c(0));
        final AbstractExpression gauss2 = f("gauss", vx, vy, vz, c(1));
        final AbstractExpression gauss3 = f("gauss", c(1), c(2), c(3), vx);
        final AbstractExpression gauss4 = f("gauss", f("+", vx, vy), f("-", vy, vz), f("*", vz, vx), f("/", vx, c(3)));
        tests(
                f("gauss", c(1), c(2), c(3), c(4)),
                f("gauss", c(0), vx, vy, vz),
                f("gauss", c(0), c(0), c(1), vz),
                gauss1,
                gauss2,
                gauss3,
                gauss4,
                f("gauss", gauss1, gauss2, gauss3, gauss4)
        );
    }}

    private static double gauss(final double a, final double b, final double c, final double x) {
        final double q = (x - b) / c;
        return a * Math.exp(-q * q / 2);
    }

    protected ObjectGaussTest(final int mode, final Language language) {
        super(mode, language);
        simplifications.addAll(List.of(
                new int[]{1, 1, 1},
                new int[]{1, 1, 1},
                new int[]{1, 1, 1},
                new int[]{34, 57, 64},
                new int[]{31, 51, 58},
                new int[]{47, 1, 1},
                new int[]{247, 129, 185},
                new int[]{1007, 693, 763}
        ));
    }

    public static void main(final String... args) {
        test(ObjectGaussTest.class, ObjectGaussTest::new, new SquareSqrtTests(), args, GAUSS_DIALECT);
    }
}
