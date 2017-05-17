package aspects;

import application.Main;
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
public aspect BinomialCacheAspect extends AbstractAspect {

    private Map<BinomMapKey, Long> cache = new HashMap<>(500);

    @Override
    protected void beforeFirstCall() {
        if (Main.CachingEnabled) {
            cache = new HashMap<>(500);
        }
    }

    @Override
    protected void afterFirstCall() {
        if (Main.CachingEnabled) {
            cache = null;
        }
    }

    long around(int n,
                int m): if(application.Main.CachingEnabled) && allCallsWithArgs(n,m ) {
        final BinomMapKey key = new BinomMapKey(n, m);
        Long value;
        if ((value = cache.get(key)) == null) {
            value = proceed(n, m);
            // Will be null after last call, no need to cache anymore
            if (cache != null) {
                cache.put(key, value);
            }
        }

        return value;
    }
}
