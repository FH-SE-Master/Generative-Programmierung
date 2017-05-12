package application;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public class BinomialCoefficient {

    public static long calculate(int n,
                                 int m) {
        return (m == 0 || m == n) ? 1L
                : calculate(n - 1, m - 1) + calculate(n - 1, m);
    }
}
