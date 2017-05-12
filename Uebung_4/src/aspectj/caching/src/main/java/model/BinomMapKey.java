package model;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/12/17
 */
public class BinomMapKey {

    private final int n;
    private final int m;

    public BinomMapKey(int n,
                       int m) {
        this.n = n;
        this.m = m;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinomMapKey that = (BinomMapKey) o;

        if (n != that.n) return false;
        return m == that.m;
    }

    @Override
    public int hashCode() {
        int result = n;
        result = 31 * result + m;
        return result;
    }
}
