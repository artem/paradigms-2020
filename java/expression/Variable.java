package expression;

import java.util.Objects;

public class Variable<E> implements Expression<E> {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public E evaluate(E x, E y, E z) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            Variable second = (Variable) obj;
            return Objects.equals(variable, second.variable);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(variable);
    }
}
