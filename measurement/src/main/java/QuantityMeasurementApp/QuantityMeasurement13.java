public class QuantityMeasurementApp13 {

    public static void main(String[] args) {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(6, LengthUnit.INCHES);

        System.out.println("ADD: " + q1.add(q2));
        System.out.println("SUBTRACT: " + q1.subtract(q2));

        double ratio = q1.divide(
                new Quantity<>(2, LengthUnit.FEET));

        System.out.println("DIVIDE: " + ratio);
    }
}