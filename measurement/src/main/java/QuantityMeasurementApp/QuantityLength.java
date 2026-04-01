package QuantityMeasurementApp;

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;

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

        // Reflexive check
        if (this == obj) {
            return true;
        }

        // Null or different class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Type casting
        QuantityLength other = (QuantityLength) obj;

        // Compare after converting to same base unit (feet)
        return Double.compare(this.toFeet(), other.toFeet()) == 0;
    }

    // (Optional but recommended)
    @Override
    public int hashCode() {
        return Double.hashCode(toFeet());
    }

    // (Optional helper for debugging)
    @Override
    public String toString() {
        return "QuantityLength{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}