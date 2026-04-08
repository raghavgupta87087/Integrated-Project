enum LengthUnit6 {
    FEET(1.0),
    INCH(1.0 / 12),
    YARD(3.0),
    CENTIMETER(1.0 / 30.48);

    private final double value;

    LengthUnit(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}