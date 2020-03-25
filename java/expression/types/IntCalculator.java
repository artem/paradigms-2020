package expression.types;

import expression.exceptions.DBZException;

public class IntCalculator implements Calculator<Integer> {
    @Override
    public Integer min(Integer val1, Integer val2) {
        return Math.min(val1, val2);
    }

    @Override
    public Integer max(Integer val1, Integer val2) {
        return Math.max(val1, val2);
    }

    @Override
    public Integer add(Integer val1, Integer val2) {
        return val1 + val2;
    }

    @Override
    public Integer sub(Integer val1, Integer val2) {
        return val1 - val2;
    }

    @Override
    public Integer mul(Integer val1, Integer val2) {
        return val1 * val2;
    }

    @Override
    public Integer div(Integer val1, Integer val2) {
        if (val2 == 0) {
            throw new DBZException(val1, val2);
        }
        return val1 / val2;
    }

    @Override
    public Integer negate(Integer val) {
        return -val;
    }

    @Override
    public Integer count(Integer val) {
        return Integer.bitCount(val);
    }

    @Override
    public Integer parse(String val) {
        return Integer.parseInt(val);
    }

    @Override
    public Integer cast(int val) {
        return val;
    }
}
