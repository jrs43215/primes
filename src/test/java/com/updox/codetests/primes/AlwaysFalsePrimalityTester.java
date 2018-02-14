package com.updox.codetests.primes;

public class AlwaysFalsePrimalityTester implements PrimalityTester {

    @Override
    public boolean isPrime(int value) {
        return false;
    }

}