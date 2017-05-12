package aspects;

import model.BinomMapKey;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the caching aspect which caches the calculated value for n,m and returns the cached value if already calculated,
 * otherwise calculates it and caches it for the occurrence of n,m.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public aspect CachingAspect {

    private Map<BinomMapKey, Long> cache = new HashMap<>(500);

    pointcut binomCall(int n,
                       int m):
            if(application.Main.ActivateCaching)
                    && call(long application.BinomialCoefficient.calculate(int, int))
                    && args(n,m)
                    && !within(CachingAspect);

    long around(int n,
                int m): binomCall(n, m) {
        final BinomMapKey key = new BinomMapKey(n, m);
        Long value;
        if ((value = cache.get(key)) == null) {
            value = proceed(n, m);
            cache.put(key, value);
        }

        return value;
    }
}
