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

    @Override
    public CheckedInt min(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;

        return new CheckedInt(Math.min(a, b));
    }

    @Override
    public CheckedInt max(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;

        return new CheckedInt(Math.max(a, b));
    }

    @Override
    public CheckedInt add(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;
        int result = a + b;
        if ((a > 0 && b > 0 && result <= 0) || (a < 0 && b < 0 && result >= 0)) {
            throw new OverflowException(this, second);
        }
        return new CheckedInt(result);
    }

    @Override
    public CheckedInt sub(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;
        int result = a - b;
        if ((a >= 0 && b < 0 && result <= 0) || (a < 0 && b > 0 && result >= 0)) {
            throw new OverflowException(this, second);
        }
        return new CheckedInt(result);
    }

    @Override
    public CheckedInt mul(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;
        int result = a * b;
        if (a > 0) {
            if (b > 0 && a > Integer.MAX_VALUE / b) {
                throw new OverflowException(this, second);
            }
            if (b < Integer.MIN_VALUE / a) {
                throw new OverflowException(this, second);
            }
        } else {
            if (b > 0 && a < Integer.MIN_VALUE / b) {
                throw new OverflowException(this, second);
            }
            if  (a != 0 && b < Integer.MAX_VALUE / a) {
                throw new OverflowException(this, second);
            }
        }
        return new CheckedInt(result);
    }

    @Override
    public CheckedInt div(CheckedInt second) {
        int a = (int) this.val;
        int b = (int) second.val;
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException(this, second);
        }
        if (b == 0) {
            throw new DBZException(this, second);
        }
        return new CheckedInt(a / b);
    }

    @Override
    public CheckedInt negate() {
        int a = (int) this.val;
        if (a == Integer.MIN_VALUE) {
            throw new UnderflowException(this);
        }
        return new CheckedInt(-a);
    }

    @Override
    public CheckedInt count() {
        return new CheckedInt(String.valueOf(Integer.bitCount((Integer) this.val)));
    }
}
