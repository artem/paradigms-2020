package expression.types;

import expression.exceptions.DBZException;
import expression.exceptions.OverflowException;
import expression.exceptions.UnderflowException;

public class CheckedIntCalculator implements Calculator<Integer> {
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
        int result = val1 + val2;
        if ((val1 > 0 && val2 > 0 && result <= 0) || (val1 < 0 && val2 < 0 && result >= 0)) {
            throw new OverflowException(val1, val2);
        }
        return result;
    }

    @Override
    public Integer sub(Integer val1, Integer val2) {
        int result = val1 - val2;
        if ((val1 >= 0 && val2 < 0 && result <= 0) || (val1 < 0 && val2 > 0 && result >= 0)) {
            throw new OverflowException(val1, val2);
        }
        return result;
    }

    @Override
    public Integer mul(Integer val1, Integer val2) {
        int result = val1 * val2;
        if (val1 > 0) {
            if (val2 > 0 && val1 > Integer.MAX_VALUE / val2) {
                throw new OverflowException(val1, val2);
            }
            if (val2 < Integer.MIN_VALUE / val1) {
                throw new OverflowException(val1, val2);
            }
        } else {
            if (val2 > 0 && val1 < Integer.MIN_VALUE / val2) {
                throw new OverflowException(val1, val2);
            }
            if  (val1 != 0 && val2 < Integer.MAX_VALUE / val1) {
                throw new OverflowException(val1, val2);
            }
        }
        return result;
    }

    @Override
    public Integer div(Integer val1, Integer val2) {
        if (val1 == Integer.MIN_VALUE && val2 == -1) {
            throw new OverflowException(val1, val2);
        }
        if (val2 == 0) {
            throw new DBZException(val1, val2);
        }
        return val1 / val2;
    }

    @Override
    public Integer negate(Integer val) {
        if (val == Integer.MIN_VALUE) {
            throw new UnderflowException(val);
        }
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
