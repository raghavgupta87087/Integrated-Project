<<<<<<< HEAD
package QuantityMeasurementApp;

public enum LengthUnit {

    // UC3 Units
    FEET(1.0),
    INCHES(1.0 / 12.0),

    // UC4 Added Units
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double conversionFactor;

    // Constructor
    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    // Convert any unit to base unit (feet)
    public double toFeet(double value) {
        return value * conversionFactor;
=======
enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toFeet(double value) {
        return value * factor;
>>>>>>> b2cfa3b (Updated files)
    }
}