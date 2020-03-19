package expression.types;

import expression.exceptions.DBZException;

public class LongCalculator implements Calculator<Long> {
    @Override
    public Long min(Long val1, Long val2) {
        return Math.min(val1, val2);
    }

    @Override
    public Long max(Long val1, Long val2) {
        return Math.max(val1, val2);
    }

    @Override
    public Long add(Long val1, Long val2) {
        return val1 + val2;
    }

    @Override
    public Long sub(Long val1, Long val2) {
        return val1 - val2;
    }

    @Override
    public Long mul(Long val1, Long val2) {
        return val1 * val2;
    }

    @Override
    public Long div(Long val1, Long val2) {
        if (val2 == 0) {
            throw new DBZException(val1, val2);
        }
        return val1 / val2;
    }

    @Override
    public Long negate(Long val) {
        return -val;
    }

    @Override
    public Long count(Long val) {
        return (long) Long.bitCount(val);
    }

    @Override
    public Long parse(String val) {
        return Long.parseLong(val);
    }

    @Override
    public Long cast(int val) {
        return (long) val;
    }
}
