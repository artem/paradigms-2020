package expression.types;

import java.math.BigInteger;

public class BigInt extends Value<BigInt> {

    public BigInt(BigInteger num) {
        super(num);
    }

    public BigInt(String s) {
        this(new BigInteger(s));
    }

    public BigInt add(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        return new BigInt(a.add(b));
    }

    public BigInt sub(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        return new BigInt(a.subtract(b));
    }

    public BigInt mul(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        return new BigInt(a.multiply(b));
    }

    public BigInt div(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        if (b.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("oof");//DBZException(this); FIXME
        }
        return new BigInt(a.divide(b));
    }

    public BigInt negate() {
        return new BigInt(((BigInteger) this.val).negate());
    }
}
