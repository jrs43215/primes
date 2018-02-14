package com.updox.codetests.primes;

/**
 * This would allow classes from other packages to be found by Cli.
 * It also helps avoid dealing with potential ClassNotFoundExceptions.
 */
public class PrimalityTesterInfo {
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
