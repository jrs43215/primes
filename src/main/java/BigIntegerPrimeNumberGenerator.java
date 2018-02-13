import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BigIntegerPrimeNumberGenerator implements PrimeNumberGenerator {

    private int certainty;

    public BigIntegerPrimeNumberGenerator() {
        this.certainty = 10;
    }

    public BigIntegerPrimeNumberGenerator(int certainty) {
        this.certainty = certainty;
    }
    @Override
    public List<Integer> generate(int startingValue, int endingValue) {
        List<Integer> primes = new ArrayList<>();
        int start, end;
        if (startingValue < endingValue) {
            start = startingValue;
            end = endingValue;
        } else {
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
        return isPrime(BigInteger.valueOf(value));
    }

    private boolean isPrime(BigInteger value) {
        if (value.compareTo(BigInteger.valueOf(2)) < 0) {
            return false;
        }
        return value.isProbablePrime(this.certainty);
    }
}
