public QuantityLength6 add(QuantityLength other) {
    if (other == null || this.unit == null || other.unit == null) {
        throw new IllegalArgumentException("Invalid input");
    }

    // Convert both to FEET (base unit)
    double valueInFeet1 = this.value * this.unit.getValue();
    double valueInFeet2 = other.value * other.unit.getValue();

    // Add
    double sumInFeet = valueInFeet1 + valueInFeet2;

    // Convert back to current unit
    double resultValue = sumInFeet / this.unit.getValue();

    return new QuantityLength(resultValue, this.unit);
}