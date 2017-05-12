package application;

public class PositiveValues {

    private int[] positiveValues;
    private int last;

    public PositiveValues(int size) {
        this.positiveValues = new int[size];
        last = 0;
    }

    public int[] getPositiveValues() {
        return positiveValues;
    }

    public void setPositiveValues(int idx,
                                  int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Error: only positive values are supported");
        }
        positiveValues[idx] = value;
    }

    public void addPositiveValue(int value) {
        setPositiveValues(last, value);
    }
}