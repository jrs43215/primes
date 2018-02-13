import java.math.BigInteger;

public class ProbabilisticPrimalityTester implements PrimalityTester {
    private int certainty;

    public ProbabilisticPrimalityTester() {
        this.certainty = 10;
    }

    public ProbabilisticPrimalityTester(int certainty) {
        this.certainty = certainty;
    }

    @Override
    public boolean isPrime(int value) {
        return isPrime(BigInteger.valueOf(value));
    }

    private boolean isPrime(BigInteger value) {
        if (value.compareTo(BigInteger.valueOf(2)) < 0) {
            return false;
        }
        return value.isProbablePrime(this.certainty);
    }
}
