package com.updox.codetests.primes;

public class AlwaysTruePrimalityTester implements PrimalityTester {

    @Override
    public boolean isPrime(int value) {
        return true;
    }

}