public class QuantityMeasurementApp14 {

    public static void main(String[] args) {

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(32, TemperatureUnit.FAHRENHEIT);

        System.out.println("Equality: " + t1.equals(t2));

        Quantity<TemperatureUnit> converted =
                t1.convertTo(TemperatureUnit.FAHRENHEIT);

        System.out.println("0°C → F = " + converted);

        try {
            t1.add(new Quantity<>(10, TemperatureUnit.CELSIUS));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}