package expression;

public abstract class Operation<E> implements Expression<E> {
    protected abstract String getOperation();
}
