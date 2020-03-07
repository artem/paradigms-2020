package expression.exceptions;

import expression.CommonExpression;

public class CheckedMultiply extends expression.Multiply {
    public CheckedMultiply(CommonExpression vx, CommonExpression vy) {
        super(vx, vy);
    }

    @Override
    protected int calculate(int a, int b) {
        int result = super.calculate(a, b);

        if (a > 0) {
            if (b > 0 && a > Integer.MAX_VALUE / b) {
                throw new OverflowException(this);
            }
            if (b < Integer.MIN_VALUE / a) {
                throw new OverflowException(this);
            }
        } else {
            if (b > 0 && a < Integer.MIN_VALUE / b) {
                throw new OverflowException(this);
            }
            if  (a != 0 && b < Integer.MAX_VALUE / a) {
                throw new OverflowException(this);
            }
        }
        return result;
    }
}
