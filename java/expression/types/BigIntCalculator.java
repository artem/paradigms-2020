package expression.types;

import expression.exceptions.DBZException;

import java.math.BigInteger;

public class BigIntCalculator implements Calculator<BigInteger> {
    @Override
    public BigInteger min(BigInteger val1, BigInteger val2) {
        return val1.min(val2);
    }

    @Override
    public BigInteger max(BigInteger val1, BigInteger val2) {
        return val1.max(val2);
    }

    @Override
    public BigInteger add(BigInteger val1, BigInteger val2) {
        return val1.add(val2);
    }

    @Override
    public BigInteger sub(BigInteger val1, BigInteger val2) {
        return val1.subtract(val2);
    }

    @Override
    public BigInteger mul(BigInteger val1, BigInteger val2) {
        return val1.multiply(val2);
    }

    @Override
    public BigInteger div(BigInteger val1, BigInteger val2) {
        if (val2.equals(BigInteger.ZERO)) {
            throw new DBZException(val1, val2);
        }
        return val1.divide(val2);
    }

    @Override
    public BigInteger negate(BigInteger val) {
        return val.negate();
    }

    @Override
    public BigInteger count(BigInteger val) {
        return BigInteger.valueOf(val.bitCount());
    }

    @Override
    public BigInteger parse(String val) {
        return new BigInteger(val);
    }

    @Override
    public BigInteger cast(int val) {
        return BigInteger.valueOf(val);
    }
}
