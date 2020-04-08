package jstest.prefix;

import jstest.ArithmeticTests;
import jstest.BaseJavascriptTest;
import jstest.Language;
import jstest.object.ObjectExpressionTest;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrefixAtanExpTest extends PrefixParsingErrorTest {
    public static final BaseJavascriptTest.Dialect ATAN_EXP_DIALECT = ObjectExpressionTest.ARITHMETIC_DIALECT.copy()
            .rename("atan", "ArcTan")
            .rename("exp", "Exp");

    public static class AtanExpTests extends ArithmeticTests {{
        unary("atan", Math::atan);
        unary("exp", Math::exp);
    }}

    protected PrefixAtanExpTest(final int mode, final Language language, final String toString) {
        super(mode, language, toString);
    }

    public static void main(final String... args) {
        test(PrefixAtanExpTest.class, PrefixAtanExpTest::new, new AtanExpTests(), args, ATAN_EXP_DIALECT, "prefix");
    }
}
