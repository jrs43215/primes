package com.updox.codetests.primes;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestCli {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PrimalityTesterCliOption getAlwaysTrueOptions() {
        PrimalityTesterCliOption option = new PrimalityTesterCliOption();
        option.setTesterClass(AlwaysTruePrimalityTester.class);
        option.setDescription("always true");
        return option;
    }

    private PrimalityTesterCliOption getAlwaysFalseOptions() {
        PrimalityTesterCliOption option = new PrimalityTesterCliOption();
        option.setTesterClass(AlwaysFalsePrimalityTester.class);
        option.setDescription("always false");
        return option;
    }

    @Test
    public void testRunNoArgs() {
        Cli cli = new Cli();
        String[] args = {};
        List<String> results = cli.run(args);
        assertEquals(results, Arrays.asList(cli.getHelpMessage()));
    }

    @Test
    public void testRunIsPrimeAlwaysTrue() {
        Map<String, PrimalityTesterCliOption> testersToOptions = new HashMap<>();
        testersToOptions.put("t", getAlwaysTrueOptions());
        String[] args = {"1", "t"};
        Cli cli = new Cli(testersToOptions, AlwaysFalsePrimalityTester.class);
        List<String> results = cli.run(args);
        assertEquals("1 is prime.", results.get(0));
    }

    @Test
    public void testRunIsPrimeAlwaysFalse() {
        Map<String, PrimalityTesterCliOption> testersToOptions = new HashMap<>();
        testersToOptions.put("f", getAlwaysFalseOptions());
        String[] args = {"1", "f"};
        Cli cli = new Cli(testersToOptions, AlwaysTruePrimalityTester.class);
        List<String> results = cli.run(args);
        assertEquals("1 is not prime.", results.get(0));
    }

    @Test
    public void testRunInvalidClassName() {
        Map<String, PrimalityTesterCliOption> testersToOptions = new HashMap<>();
        testersToOptions.put("f", getAlwaysFalseOptions());
        String[] args = {"1", "t"};
        Cli cli = new Cli(testersToOptions, AlwaysFalsePrimalityTester.class);
        List<String> results = cli.run(args);
        assertEquals(1, results.size());
        assertEquals(cli.getHelpMessage(), results.get(0));
    }

    @Test
    public void testRunIntegerTooLarge() {
        Cli cli = new Cli();
        String[] args = {"2147483648"};
        List<String> results = cli.run(args);
        assertEquals(1, results.size());
        assertEquals(cli.getHelpMessage(), results.get(0));
    }

    @Test
    public void testRunTooManyArguments() {
        Cli cli = new Cli();
        String[] args = {"1", "2", "3", "4"};
        List<String> results = cli.run(args);
        assertEquals(1, results.size());
        assertEquals(cli.getHelpMessage(), results.get(0));
    }

    @Test
    public void testRunTwoArgsAllTrue() {
        Map<String, PrimalityTesterCliOption> testersToOptions = new HashMap<>();
        String[] args = {"1", "3"};
        Cli cli = new Cli(testersToOptions, AlwaysTruePrimalityTester.class);
        List<String> results = cli.run(args);
        assertEquals(3, results.size());
        assertEquals("1", results.get(0));
        assertEquals("2", results.get(1));
        assertEquals("3", results.get(2));
    }

    @Test
    public void testRunTwoArgsAllFalse() {
        Map<String, PrimalityTesterCliOption> testersToOptions = new HashMap<>();
        String[] args = {"1", "3"};
        Cli cli = new Cli(testersToOptions, AlwaysFalsePrimalityTester.class);
        List<String> results = cli.run(args);
        assertEquals(1, results.size());
        assertEquals("No primes found in range.", results.get(0));
    }

    @Test
    public void testMainRunsWithoutArgs() {
        String[] args = {};
        Cli.main(args);
    }

}
