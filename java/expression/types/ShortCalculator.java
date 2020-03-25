package expression.types;

import expression.exceptions.DBZException;

public class ShortCalculator implements Calculator<Short> {
    @Override
    public Short min(Short val1, Short val2) {
        return (short) Math.min(val1, val2);
    }

    @Override
    public Short max(Short val1, Short val2) {
        return (short) Math.max(val1, val2);
    }

    @Override
    public Short add(Short val1, Short val2) {
        return (short) (val1 + val2);
    }

    @Override
    public Short sub(Short val1, Short val2) {
        return (short) (val1 - val2);
    }

    @Override
    public Short mul(Short val1, Short val2) {
        return (short) (val1 * val2);
    }

    @Override
    public Short div(Short val1, Short val2) {
        if (val2 == 0) {
            throw new DBZException(val1, val2);
        }
        return (short) (val1 / val2);
    }

    @Override
    public Short negate(Short val) {
        return (short) -val;
    }

    @Override
    public Short count(Short val) {
        return (short) Integer.bitCount(val & 0xFFFF);
    }

    @Override
    public Short parse(String val) {
        return Short.parseShort(val);
    }

    @Override
    public Short cast(int val) {
        return (short) val;
    }
}
