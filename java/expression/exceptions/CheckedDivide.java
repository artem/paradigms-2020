package expression.exceptions;

import expression.CommonExpression;

public class CheckedDivide extends expression.Divide {
    public CheckedDivide(CommonExpression vx, CommonExpression vy) {
        super(vx, vy);
    }

    @Override
    public int calculate(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException(this);
        }
        if (b == 0) {
            throw new DBZException(this);
        }
        return super.calculate(a, b);
    }
}
