package expression.types;

import expression.exceptions.DBZException;

import static java.lang.Long.parseLong;

public class Long extends Value<Long> {
    public Long(String s) {
        this(parseLong(s));
    }

    public Long(long num) {
        super(num);
    }

    @Override
    public Long min(Long second) {
        long a = (long) this.val;
        long b = (long) second.val;

        return new Long(Math.min(a, b));
    }

    @Override
    public Long max(Long second) {
        long a = (long) this.val;
        long b = (long) second.val;

        return new Long(Math.max(a, b));
    }

    public Long add(Long second) {
        long a = (long) this.val;
        long b = (long) second.val;

        return new Long(a + b);
    }

    public Long sub(Long second) {
        long a = (long) this.val;
        long b = (long) second.val;

        return new Long(a - b);
    }

    public Long mul(Long second) {
        long a = (long) this.val;
        long b = (long) second.val;

        return new Long(a * b);
    }

    public Long div(Long second) {
        long a = (long) this.val;
        long b = (long) second.val;

        if (b == 0) {
            throw new ArithmeticException("oof");//DBZException(this); FIXME
        }
        return new Long(a / b);
    }

    public Long negate() {
        return new Long(-((long) this.val));
    }

    @Override
    public Long count() {
        return new Long(String.valueOf(java.lang.Long.bitCount((long) this.val)));
    }
}
