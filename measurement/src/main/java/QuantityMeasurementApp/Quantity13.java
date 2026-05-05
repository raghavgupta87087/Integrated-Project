import java.util.Objects;

public class Quantity13<U extends Enum<U> & IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }

    /* =========================================
       UC13 CORE ENUM (DRY LOGIC)
       ========================================= */
    private enum ArithmeticOperation {
        ADD {
            double compute(double a, double b) { return a + b; }
        },
        SUBTRACT {
            double compute(double a, double b) { return a - b; }
        },
        DIVIDE {
            double compute(double a, double b) {
                if (Math.abs(b) < 1e-12)
                    throw new ArithmeticException("Divide by zero");
                return a / b;
            }
        };

        abstract double compute(double a, double b);
    }

    /* =========================================
       COMMON VALIDATION (DRY)
       ========================================= */
    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean checkTarget) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity null");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Different categories");

        if (Double.isNaN(other.value) || Double.isInfinite(other.value))
            throw new IllegalArgumentException("Invalid other value");

        if (checkTarget && targetUnit == null)
            throw new IllegalArgumentException("Target unit null");

        if (checkTarget && !unit.getClass().equals(targetUnit.getClass()))
            throw new IllegalArgumentException("Invalid target unit");
    }

    /* =========================================
       CORE METHOD (DRY)
       ========================================= */
    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation op) {

        double a = unit.convertToBaseUnit(value);
        double b = other.unit.convertToBaseUnit(other.value);

        return op.compute(a, b);
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    /* =========================================
       PUBLIC METHODS (UNCHANGED API)
       ========================================= */

    public Quantity<U> add(Quantity<U> other) {
        return add(other, unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double base = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double result = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(round2(result), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double base = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double result = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(round2(result), targetUnit);
    }

    public double divide(Quantity<U> other) {

        validateArithmeticOperands(other, null, false);

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Quantity<?> q)) return false;
        return Math.abs(value - q.value) < 0.0001 &&
                Objects.equals(unit, q.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}