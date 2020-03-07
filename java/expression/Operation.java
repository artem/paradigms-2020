package expression;

public abstract class Operation implements CommonExpression {
    @Override
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    protected abstract String getOperation();
}
