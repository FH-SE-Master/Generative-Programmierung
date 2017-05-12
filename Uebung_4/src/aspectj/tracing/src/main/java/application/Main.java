package application;

public class Main {

    public static void main(String args[]) {
        PositiveValues value = new PositiveValues(10);
        try {
            value.addPositiveValue(1);
            value.addPositiveValue(-1);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}