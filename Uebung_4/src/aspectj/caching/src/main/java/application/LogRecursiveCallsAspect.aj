package application;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public aspect LogRecursiveCallsAspect {
    private int callCount;

    // Log the first call
    pointcut upperLevelLogging():
            if(Main.ActivateLogging)
                    && call(long BinomialCoefficient.calculate(..))
                    && !within(BinomialCoefficient);

    // Log all other calls
    pointcut lowerLevelLogging():
            if(Main.ActivateLogging)
                    && call(long BinomialCoefficient.calculate(..))
                    && within(BinomialCoefficient);

    before(): upperLevelLogging() {
        callCount = 0;
    }

    before(): lowerLevelLogging() {
        callCount++;
    }

    after(): upperLevelLogging() {
        System.out.println("Recursive calls: " + callCount);
    }
}
