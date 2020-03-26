package expression;

import expression.types.Calculator;

public interface Expression<E extends Number> {
    E evaluate(E x, E y, E z, Calculator<E> op);
}
