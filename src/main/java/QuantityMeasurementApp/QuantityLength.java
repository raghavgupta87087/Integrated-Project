public class QuantityLength {

    double value;
    LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    // ✅ UC7 method
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double valueInFeet1 = this.value * this.unit.getValue();
        double valueInFeet2 = other.value * other.unit.getValue();

        double sumInFeet = valueInFeet1 + valueInFeet2;

        double resultValue = sumInFeet / targetUnit.getValue();

        return new QuantityLength(resultValue, targetUnit);
    }
}