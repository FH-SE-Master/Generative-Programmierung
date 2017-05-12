package application;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public aspect CachingAspect {

    private Map<String, Long> cache = new HashMap<>();

    pointcut bcCall(int n,
                    int m):
            if(Main.ActivateCaching)
                    && call(long BinomialCoefficient.calculate(int, int))
                    && args(n,m)
                    && !within(CachingAspect);

    long around(int n,
                int m): bcCall(n, m) {
        // if in cache then return cached
        final String key = n + "-" + m;
        Long cachedValue = cache.get(key);
        if (cachedValue == null) {
            cachedValue = proceed(n, m);
        }

        return cachedValue;
    }
}
