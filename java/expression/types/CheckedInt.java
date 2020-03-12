package expression.types;

import expression.exceptions.DBZException;
import expression.exceptions.OverflowException;
import expression.exceptions.UnderflowException;

public class CheckedInt extends Value<CheckedInt> {
    public CheckedInt(String s) {
        this(Integer.parseInt(s));
    }

    public CheckedInt(int num) {
        super(num);
    }

    public CheckedInt add(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;
        int result = a + b;
        if ((a > 0 && b > 0 && result <= 0) || (a < 0 && b < 0 && result >= 0)) {
            throw new ArithmeticException("oof");//OverflowException(this); FIXME
        }
        return new CheckedInt(result);
    }

    public CheckedInt sub(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;
        int result = a - b;
        if ((a >= 0 && b < 0 && result <= 0) || (a < 0 && b > 0 && result >= 0)) {
            throw new ArithmeticException("oof");//OverflowException(this);
        }
        return new CheckedInt(result);
    }

    public CheckedInt mul(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;
        int result = a * b;
        if (a > 0) {
            if (b > 0 && a > Integer.MAX_VALUE / b) {
                throw new ArithmeticException("oof");//OverflowException(this);
            }
            if (b < Integer.MIN_VALUE / a) {
                throw new ArithmeticException("oof");//OverflowException(this);
            }
        } else {
            if (b > 0 && a < Integer.MIN_VALUE / b) {
                throw new ArithmeticException("oof");//OverflowException(this);
            }
            if  (a != 0 && b < Integer.MAX_VALUE / a) {
                throw new ArithmeticException("oof");//OverflowException(this);
            }
        }
        return new CheckedInt(result);
    }

    public CheckedInt div(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ArithmeticException("oof");//OverflowException(this);
        }
        if (b == 0) {
            throw new ArithmeticException("oof");//DBZException(this);
        }
        return new CheckedInt(a / b);
    }

    public CheckedInt negate() {
        int a = (int) this.val;
        if (a == Integer.MIN_VALUE) {
            throw new ArithmeticException("oof");//UnderflowException(this);
        }
        return new CheckedInt(-a);
    }
}
