package com.updox.codetests.primes;

/**
 * This would allow classes from other packages to be found by Cli.
 * It also helps avoid dealing with potential ClassNotFoundExceptions.
 *
 * Although I didn't end up doing this, I used a Class rather than just storing
 * a PrimalityTester object so that constructor arguments could be passed in the CLI.
 * (ProbabilisticPrimalityGenerator originally had the option to adjust the certainty).
 */
public class PrimalityTesterCliOption {
    private Class testerClass;
    private String description;

    public Class getTesterClass() {
        return testerClass;
    }

    public void setTesterClass(Class testerClass) {
        this.testerClass = testerClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
