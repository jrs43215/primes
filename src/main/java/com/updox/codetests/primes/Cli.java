package com.updox.codetests.primes;

import java.util.*;
import java.util.stream.Collectors;

public class Cli {

    private Map<String, PrimalityTesterCliOption> testerNameToOption;

    private Class defaultPrimalityTester;

    public Cli() {
        PrimalityTesterCliOption naiveOption = new PrimalityTesterCliOption();
        naiveOption.setTesterClass(NaivePrimalityTester.class);
        naiveOption.setDescription("(default): a simple trial division implementation");

        PrimalityTesterCliOption probabilisticOption = new PrimalityTesterCliOption();
        probabilisticOption.setTesterClass(ProbabilisticPrimalityTester.class);
        probabilisticOption.setDescription("uses probablistic test built in to Java's BigInteger");

        this.testerNameToOption = new HashMap<>();
        this.testerNameToOption.put(NaivePrimalityTester.class.getSimpleName(),
                naiveOption);
        this.testerNameToOption.put(ProbabilisticPrimalityTester.class.getSimpleName(),
                probabilisticOption);

        this.defaultPrimalityTester = NaivePrimalityTester.class;
    }

    public Cli(Map<String, PrimalityTesterCliOption> primalityTestersToDescriptions, Class defaultPrimalityTester) {
        this.testerNameToOption = primalityTestersToDescriptions;
        this.defaultPrimalityTester = defaultPrimalityTester;
    }

    public List<String> run(String[] args) {

        if (!validateArgs(args)) {
            return Arrays.asList(getHelpMessage());
        }

        CliArguments parsed = parseArgs(args);

        // This unchecked assignment should be safe after the validation & parsing phases.
        PrimeNumberGenerator generator = buildGenerator(parsed.getTesterClass());

        return runCommand(generator, parsed.getFirst(), parsed.getSecond());
    }

    /**
     * Performs basic validation on arguments.
     *
     * @param args The arguments being passed to the CLI
     * @return whether or not the arguments are valid
     */
    private boolean validateArgs(String[] args) {
        // Make sure we have the correct number of arguments
        if (args.length > 3 || args.length == 0) {
            return false;
        }

        // Make sure all arguments are either valid integers, or valid PrimalityTest class names
        for (String arg : args) {
            try {
                Integer intArg = Integer.parseInt(arg);
            } catch (NumberFormatException exc) {
                if (!testerNameToOption.containsKey(arg)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Safely attempts to parse an integer from an argument.
     *
     * @param arg The argument to be parsed
     * @return An Optional value containing the Integer if one could be parsed
     */
    private Optional<Integer> parseInteger(String arg) {
        try {
            return Optional.of(Integer.parseInt(arg));
        } catch (NumberFormatException exc) {
            return Optional.empty();
        }
    }

    /**
     * Parse arguments into a CliArguments object.
     * Defaults to NaivePrimalityTester in event of an error.
     *
     * @param args The arguments to be parsed
     * @return A CliArguments object containing the provided args
     */
    private CliArguments parseArgs(String[] args) {
        CliArguments parsedArgs = new CliArguments();
        for (String arg : args) {
            Optional<Integer> parsed = parseInteger(arg);
            if (parsed.isPresent()) {
                if (parsedArgs.getFirst() == null) {
                    parsedArgs.setFirst(parsed.get());
                } else {
                    parsedArgs.setSecond(parsed.get());
                }
            } else {
                // If validated arg wasn't an int, it's safe to assume it's a valid Class name
                PrimalityTesterCliOption option = testerNameToOption.get(arg);
                parsedArgs.setTesterClass(option.getTesterClass());
            }
        }

        return parsedArgs;
    }

    /**
     * Constructs a PrimalityTester from the given class, and uses that to build a PrimeNumberGenerator.
     *
     * @param testerClass The PrimalityTester Class to be used
     * @return A PrimeNumberGenerator using the given PrimalityTester
     * @throws IllegalArgumentException if the provided class cannot be instantiated
     */
    private PrimeNumberGenerator buildGenerator(Class testerClass) {
        PrimalityTester tester = null;
        try {
            tester = (PrimalityTester) testerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException exc) {
            System.out.println("ERROR! Could not instantiate Class with default constructor:"
                    + testerClass.getName());
            throw new IllegalArgumentException(exc); // This should never happen, just crash the program.
        }
        return new BasicPrimeNumberGenerator(tester);
    }

    /**
     * Gets the results from the appropriate method of PrimeNumberGenerator.
     *
     * @param generator The PrimeNumberGenerator that will be used.
     * @param first     The first integer parameter, which should not be null.
     * @param second    The second integer parameter, which may be null.
     * @return A list of results to be printed to the console.
     */
    private List<String> runCommand(PrimeNumberGenerator generator, Integer first, Integer second) {
        // If second is null, we just want to run isPrime
        if (second == null) {
            if (generator.isPrime(first)) {
                return Arrays.asList(first + " is prime.");
            } else {
                return Arrays.asList(first + " is not prime.");
            }
        }

        List<Integer> results = generator.generate(first, second);
        if (results.size() == 0) {
            return Arrays.asList("No primes found in range.");
        }

        return results.stream().map(r -> r.toString()).collect(Collectors.toList());
    }


    public String getHelpMessage() {
        return "Provide 1 integer for primality testing, " +
                "2 integers for generating all primes in a range, " +
                "and optionally the name of a PrimalityGenerator class.\n" +
                "The available PrimalityGenerators are:\n" +
                this.getPrimalityTesterList() +
                "(all integers must be less than 2,147,483,648)";
    }

    private String getPrimalityTesterList() {
        // It would be better to pull these in using some reflection & an annotation
        // But that felt like overkill for this project
        String testerList = "";
        for (Map.Entry<String, PrimalityTesterCliOption> entry : testerNameToOption.entrySet()) {
            testerList = testerList + entry.getKey() + " " + entry.getValue().getDescription() + "\n";
        }
        return testerList;
    }

    private class CliArguments {
        private Integer first;
        private Integer second;
        private Class testerClass;

        public CliArguments() {
            this.testerClass = defaultPrimalityTester;
        }

        public Integer getFirst() {
            return first;
        }

        public void setFirst(Integer first) {
            this.first = first;
        }

        public Integer getSecond() {
            return second;
        }

        public void setSecond(Integer second) {
            this.second = second;
        }

        public Class getTesterClass() {
            return testerClass;
        }

        public void setTesterClass(Class testerClass) {
            this.testerClass = testerClass;
        }
    }

    public static void main(String[] args) {
        new Cli().run(args).forEach(System.out::println);
    }
}
