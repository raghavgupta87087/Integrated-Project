package QuantityMeasurementApp;

public class QuantityLength {

    private double value;
    private LengthUnit unit;

    // Constructor
    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    // Convert to base unit (feet)
    private double toFeet() {
        return unit.toFeet(value);
    }

    // Override equals method
    @Override
    public boolean equals(Object obj) {

        // Same reference
        if (this == obj) {
            return true;
        }

        // Null or different type
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Type casting
        QuantityLength other = (QuantityLength) obj;

        // Compare after converting to same unit (feet)
        return Double.compare(this.toFeet(), other.toFeet()) == 0;
    }
}