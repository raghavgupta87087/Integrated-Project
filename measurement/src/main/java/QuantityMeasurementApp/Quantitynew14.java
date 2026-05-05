public Quantitynew14<U> convertTo(U targetUnit) {

    if (targetUnit == null)
        throw new IllegalArgumentException("Target unit null");

    if (!unit.getClass().equals(targetUnit.getClass()))
        throw new IllegalArgumentException("Different categories");

    double base = unit.convertToBaseUnit(value);
    double result = targetUnit.convertFromBaseUnit(base);

    return new Quantity<>(result, targetUnit);
}