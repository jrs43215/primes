package com.updox.codetests.primes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(value = Parameterized.class)
public class TestPrimeNumberGenerator {

    private Class implementation;

    private PrimeNumberGenerator generator;

    private List<Integer> firstTenPrimes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);

    public TestPrimeNumberGenerator(Class implementation) {
        this.implementation = implementation;
    }

    @Before
    public void initialize() throws IllegalAccessException, InstantiationException {
        generator = new BasicPrimeNumberGenerator((PrimalityTester) implementation.newInstance());
    }

    @Parameterized.Parameters
    public static Collection getImplementations() {
        return Arrays.asList(NaivePrimalityTester.class, ProbabilisticPrimalityTester.class);
    }

    @Test
    public void testIsPrime() {
        assertFalse(generator.isPrime(1));
        firstTenPrimes.forEach(prime -> {
            assertTrue(generator.isPrime(prime));
        });
    }

    @Test
    public void testGenerate() {
        List<Integer> expected = Arrays.asList(7901, 7907, 7919);
        assertEquals(expected, generator.generate(7900, 7920));
    }

    @Test
    public void testGenerateFirstTen() {
        assertEquals(firstTenPrimes, generator.generate(1, 30));
    }

    @Test
    public void testGenerateFirstTenInverse() {
        assertEquals(firstTenPrimes, generator.generate(30, 1));
    }

    @Test
    public void testGenerateSameInputs() {
        assertEquals(Arrays.asList(3), generator.generate(3, 3));
    }

    @Test
    public void testGenerateNegativeStart() {
        assertEquals(firstTenPrimes, generator.generate(-100, 30));
    }

    @Test
    public void testGenerateNegativeEnd() {
        assertEquals(firstTenPrimes, generator.generate(30, -100));
    }

    @Test
    public void testGenerateNegativeInputs() {
        assertEquals(Arrays.asList(), generator.generate(-30, -1));
    }


}
