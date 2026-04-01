package QuantityMeasurementApp;

public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0);

    private final double conversionFactor;

    // Constructor
    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    // Convert given value to base unit (feet)
    public double toFeet(double value) {
        return value * conversionFactor;
    }
}