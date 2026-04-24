import java.util.Objects;

public class Quantity<U extends Enum<U> & IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    /* =========================
       UC12 subtract()
       ========================= */

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validate(other);

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit null");

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        double resultBase = thisBase - otherBase;
        double result = targetUnit.convertFromBaseUnit(resultBase);

        result = Math.round(result * 100.0) / 100.0;

        return new Quantity<>(result, targetUnit);
    }

    /* =========================
       UC12 divide()
       ========================= */

    public double divide(Quantity<U> other) {

        validate(other);

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        if (Math.abs(otherBase) < 1e-12)
            throw new ArithmeticException("Divide by zero");

        return thisBase / otherBase;
    }

    private void validate(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Null quantity");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Different categories");
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quantity<?> q))
            return false;

        return Math.abs(value - q.value) < 0.0001 &&
               Objects.equals(unit, q.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}