import java.util.ArrayList;
import java.util.List;

public class NaivePrimeNumberGenerator implements PrimeNumberGenerator {
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
        if (value < 2) {
            return false;
        }

        for(int i = 2; i < value; i++) {
            if (value % i == 0) {
                return false;
            }
        }

        return true;

    }
}
