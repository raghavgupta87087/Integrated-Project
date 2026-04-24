public class QuantityMeasurementApp12 {

    public static void main(String[] args) {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(6.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result1 = q1.subtract(q2);
        System.out.println("10 ft - 6 in = " + result1);

        Quantity<LengthUnit> result2 =
                q1.subtract(q2, LengthUnit.INCHES);

        System.out.println("10 ft - 6 in = " + result2);

        double ratio = q1.divide(
                new Quantity<>(2.0, LengthUnit.FEET));

        System.out.println("10 ft / 2 ft = " + ratio);
    }
}