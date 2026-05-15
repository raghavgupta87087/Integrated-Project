public class QuantityMeasurementApp3 {

    /* =====================================
       LengthUnit Enum
       ===================================== */

    enum LengthUnit {

        FEET(1.0),
        INCHES(1.0 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    /* =====================================
       QuantityLength Class
       ===================================== */

    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(
                double value,
                LengthUnit unit
        ) {

            if (unit == null) {
                throw new IllegalArgumentException(
                        "Unit cannot be null"
                );
            }

            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        private double convertToFeet() {

            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null ||
                    getClass() != obj.getClass())
                return false;

            QuantityLength other =
                    (QuantityLength) obj;

            return Math.abs(
                    this.convertToFeet()
                            - other.convertToFeet()
            ) < 0.0001;
        }

        @Override
        public String toString() {

            return "Quantity("
                    + value
                    + ", "
                    + unit
                    + ")";
        }
    }

    /* =====================================
       Main Method
       ===================================== */

    public static void main(String[] args) {

        QuantityLength q1 =
                new QuantityLength(
                        1.0,
                        LengthUnit.FEET
                );

        QuantityLength q2 =
                new QuantityLength(
                        12.0,
                        LengthUnit.INCHES
                );

        QuantityLength q3 =
                new QuantityLength(
                        2.0,
                        LengthUnit.FEET
                );

        System.out.println(
                q1 + " equals " + q2
        );

        System.out.println(
                "Result: "
                        + q1.equals(q2)
        );

        System.out.println(
                q1 + " equals " + q3
        );

        System.out.println(
                "Result: "
                        + q1.equals(q3)
        );
    }
}