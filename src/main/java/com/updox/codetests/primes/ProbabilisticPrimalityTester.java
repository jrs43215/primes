package com.updox.codetests.primes;

import java.math.BigInteger;

public class ProbabilisticPrimalityTester implements PrimalityTester {

    @Override
    public boolean isPrime(int value) {
        return isPrime(BigInteger.valueOf(value));
    }

    private boolean isPrime(BigInteger value) {
        if (value.compareTo(BigInteger.valueOf(2)) < 0) {
            return false;
        }
        // The option to change this was removed as a certainty of 10 is sufficient for int values
        return value.isProbablePrime(10);
    }

}
