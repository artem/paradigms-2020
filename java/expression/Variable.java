package expression;

import expression.types.Calculator;

public class Variable<E> implements Expression<E> {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public E evaluate(E x, E y, E z, Calculator<E> op) {
        switch (variable) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new IllegalArgumentException("Unsupported variable name");
        }
    }

    @Override
    public String toString() {
        return variable;
    }
}
