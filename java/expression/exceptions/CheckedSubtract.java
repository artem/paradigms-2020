package expression.exceptions;

import expression.CommonExpression;

public class CheckedSubtract extends expression.Subtract {
    public CheckedSubtract(CommonExpression vx, CommonExpression vy) {
        super(vx, vy);
    }

    @Override
    protected int calculate(int a, int b) {
        int result = super.calculate(a, b);
        if ((a >= 0 && b < 0 && result <= 0) || (a < 0 && b > 0 && result >= 0)) {
            throw new OverflowException(this);
        }
        return result;
    }
}
