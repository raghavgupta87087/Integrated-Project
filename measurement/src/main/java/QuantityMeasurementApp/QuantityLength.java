package QuantityMeasurementApp;

public class QuantityLength {

<<<<<<< HEAD
    private final double value;
    private final LengthUnit unit;

    // ✅ Epsilon for floating-point comparison
    private static final double EPSILON = 0.0001;
=======
    private double value;
    private LengthUnit unit;
>>>>>>> b2cfa3b (Updated files)

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

<<<<<<< HEAD
        // ✅ Use epsilon instead of Double.compare
        return Math.abs(this.toFeet() - other.toFeet()) < EPSILON;
    }

    // ✅ Recommended when overriding equals
    @Override
    public int hashCode() {
        return Double.hashCode(toFeet());
    }

    // (Optional) helpful for debugging
    @Override
    public String toString() {
        return "QuantityLength{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
=======
        // Compare after converting to same unit (feet)
        return Double.compare(this.toFeet(), other.toFeet()) == 0;
>>>>>>> b2cfa3b (Updated files)
    }
}