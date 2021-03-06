package com.updox.codetests.primes;

import java.util.ArrayList;
import java.util.List;

public class BasicPrimeNumberGenerator implements PrimeNumberGenerator {

    private PrimalityTester primalityTester;

    public BasicPrimeNumberGenerator(PrimalityTester primalityTester) {
        this.primalityTester = primalityTester;
    }

    @Override
    public List<Integer> generate(int startingValue, int endingValue) {
        List<Integer> primes = new ArrayList<>();
        int start, end;
        if (startingValue < endingValue) {
            start = startingValue;
            end = endingValue;
        } else {
            /* This would still run if starting and ending values were the same,
               but this will still return the correct answer.
             */
            start = endingValue;
            end = startingValue;
        }

        for (int i = start; i <= end; i++) {
            if (this.isPrime(i)) {
                primes.add(i);
            }
        }

        return primes;
    }

    @Override
    public boolean isPrime(int value) {
        return this.primalityTester.isPrime(value);
    }
}
