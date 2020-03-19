package expression.types;

import expression.exceptions.DBZException;

import java.math.BigInteger;

public class BigInt extends Value<BigInt> {

    public BigInt(BigInteger num) {
        super(num);
    }

    public BigInt(String s) {
        this(new BigInteger(s));
    }

    @Override
    public BigInt min(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        return new BigInt(a.min(b));
    }

    @Override
    public BigInt max(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        return new BigInt(a.max(b));
    }

    @Override
    public BigInt add(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        return new BigInt(a.add(b));
    }

    @Override
    public BigInt sub(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        return new BigInt(a.subtract(b));
    }

    @Override
    public BigInt mul(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        return new BigInt(a.multiply(b));
    }

    @Override
    public BigInt div(BigInt second) {
        BigInteger a = (BigInteger) this.val;
        BigInteger b = (BigInteger) second.val;

        if (b.equals(BigInteger.ZERO)) {
            throw new DBZException(this, second);
        }
        return new BigInt(a.divide(b));
    }

    @Override
    public BigInt negate() {
        return new BigInt(((BigInteger) this.val).negate());
    }

    @Override
    public BigInt count() {
        return new BigInt(String.valueOf(((BigInteger) this.val).bitCount()));
    }
}
