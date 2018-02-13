public class NaivePrimalityTester implements PrimalityTester {
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
