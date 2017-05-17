package aspects;

/**
 * This is the base class for providing advice for the first calls and defines all of the used point cut.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/17/17
 */
public abstract aspect AbstractAspect {

    public pointcut firstCall():
            call(long application.BinomialCoefficient.calculate(..))
                    && !within(application.BinomialCoefficient)
                    && !within(aspects.*);

    public pointcut allCallsWithArgs(int n,
                                     int m):
            call(long application.BinomialCoefficient.calculate(int,int))
                    && args(n,m)
                    &&!within(aspects.*);

    public pointcut innerCalls():call(long application.BinomialCoefficient.calculate(..))
            && within(application.BinomialCoefficient)
            && !within(aspects.*);

    before(): firstCall() {
        beforeFirstCall();
    }

    after(): firstCall() {
        afterFirstCall();
    }

    protected void beforeFirstCall() {
        // default does nothing
    }

    protected void afterFirstCall() {
        // default does nothing
    }
}
