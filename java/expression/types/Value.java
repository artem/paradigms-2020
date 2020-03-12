package expression.types;

public abstract class Value<E> {
    protected final Number val;

    protected Value(Number val) {
        this.val = val;
    }

    public abstract E add(E val);
    public abstract E sub(E val);
    public abstract E mul(E val);
    public abstract E div(E val);
    public abstract E negate();
    public Number getVal() {
        return val;
    }
}
