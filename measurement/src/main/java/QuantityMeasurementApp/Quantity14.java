private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation op) {

    // 🔥 UC14 ADD THIS LINE
    this.unit.validateOperationSupport(op.name());

    double a = unit.convertToBaseUnit(value);
    double b = other.unit.convertToBaseUnit(other.value);

    return op.compute(a, b);
}