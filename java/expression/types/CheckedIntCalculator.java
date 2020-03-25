package expression.types;

import expression.exceptions.OverflowException;
import expression.exceptions.UnderflowException;

public class CheckedIntCalculator extends IntCalculator {
    @Override
    public Integer add(Integer val1, Integer val2) {
        int result = super.add(val1, val2);
        if ((val1 > 0 && val2 > 0 && result <= 0) || (val1 < 0 && val2 < 0 && result >= 0)) {
            throw new OverflowException(val1, val2);
        }
        return result;
    }

    @Override
    public Integer sub(Integer val1, Integer val2) {
        int result = super.sub(val1, val2);
        if ((val1 >= 0 && val2 < 0 && result <= 0) || (val1 < 0 && val2 > 0 && result >= 0)) {
            throw new OverflowException(val1, val2);
        }
        return result;
    }

    @Override
    public Integer mul(Integer val1, Integer val2) {
        int result = super.mul(val1, val2);
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
        return super.div(val1, val2);
    }

    @Override
    public Integer negate(Integer val) {
        if (val == Integer.MIN_VALUE) {
            throw new UnderflowException(val);
        }
        return super.negate(val);
    }
}
