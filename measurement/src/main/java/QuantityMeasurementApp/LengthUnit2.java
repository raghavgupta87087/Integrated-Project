enum LengthUnit2 {
    FEET(1.0),
    INCH(1.0 / 12),
    YARD(3.0),
    CENTIMETER(0.393701 / 12); // convert cm → inch → feet

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double toFeet(double value) {
        return value * toFeetFactor;
    }
}