package expression;

public abstract class Operation<E extends Number> implements Expression<E> {
    protected abstract String getOperation();
}
