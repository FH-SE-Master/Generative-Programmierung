package application;

/**
 * This class represents a positive value store
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public class PositiveValueStore {

    private int[] positiveValues;
    private int size;
    private int last = 0;

    public PositiveValueStore(int size) {
        this.positiveValues = new int[size];
        this.size = size;
    }

    public void addPositiveValue(int value) {
        setPositiveValues(last, value);
    }

    public void setPositiveValues(int idx,
                                  int value) {
        checkIdx(idx);
        checkValue(value);
        getPositiveValues()[idx] = value;
        last++;
    }

    public int[] getPositiveValues() {
        return positiveValues;
    }

    private void checkValue(final int value) {
        if (value < 0) {
            throwExceptionIfIdxInvalid();
        }
    }

    private void checkIdx(final int idx) {
        if (idx >= size) {
            throwExceptionIfValueInvalid();
        }
    }

    private void throwExceptionIfIdxInvalid() {
        throw new ArrayIndexOutOfBoundsException("Index exceeds size");
    }

    private void throwExceptionIfValueInvalid() {
        throw new IllegalArgumentException("Only positive values are supported");
    }
}