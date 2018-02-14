# primes

To run the tests and build the jacoco report (located at `build/jacocoHtml/index.html`:
`./gradlew build jacocoTestReport`

To use the CLI, first run `./gradlew build` to build the JAR.

Example CLI usage:
`java -jar build/libs/primes-1.0-SNAPSHOT.jar` will display some basic help information.
`java -jar build/libs/primes-1.0-SNAPSHOT.jar 1 10` uses the default tester to find primes between 1 and 10
`java -jar build/libs/primes-1.0-SNAPSHOT.jar 3` uses the default tester to determine whether or not 3 is prime
You can also add `ProbabilisticPrimalityGenerator` as an additional argument to use that tester instead. This is significantly faster on larger ranges.
