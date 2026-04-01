package QuantityMeasurementApp;

public class MeasurementApplication {

    // ===== UC1: Feet =====
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ===== UC2: Inches =====
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || !(obj instanceof Inches)) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ===== UC2 Methods =====
    public static boolean compareFeet(double val1, double val2) {
        return new Feet(val1).equals(new Feet(val2));
    }

    public static boolean compareInches(double val1, double val2) {
        return new Inches(val1).equals(new Inches(val2));
    }

    // ===== MAIN METHOD =====
    public static void main(String[] args) {

        // ===== UC1 =====
        System.out.println("---- UC1: Feet ----");
        System.out.println("Feet Equal: " + compareFeet(34.5, 34.5));

        // ===== UC2 =====
        System.out.println("\n---- UC2: Inches ----");
        System.out.println("Inches Equal: " + compareInches(10.0, 10.0));

        // ===== UC3 =====
        System.out.println("\n---- UC3: QuantityLength ----");

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("1 Foot == 12 Inches: " + q1.equals(q2)); // true

        QuantityLength q3 = new QuantityLength(2.0, LengthUnit.FEET);
        System.out.println("1 Foot == 2 Feet: " + q1.equals(q3)); // false

        // ===== UC4 =====
        System.out.println("\n---- UC4: Extended Units ----");

        // Yard ↔ Feet
        QuantityLength q4 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q5 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("1 Yard == 3 Feet: " + q4.equals(q5)); // true

        // Yard ↔ Inches
        QuantityLength q6 = new QuantityLength(36.0, LengthUnit.INCHES);
        System.out.println("1 Yard == 36 Inches: " + q4.equals(q6)); // true

        // CM ↔ Inches
        QuantityLength q7 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q8 = new QuantityLength(0.393701, LengthUnit.INCHES);
        System.out.println("1 cm == 0.393701 Inches: " + q7.equals(q8)); // true

        // Negative case
        QuantityLength q9 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q10 = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println("1 cm == 1 Foot: " + q9.equals(q10)); // false

        // Same unit
        QuantityLength q11 = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength q12 = new QuantityLength(2.0, LengthUnit.YARDS);
        System.out.println("2 Yards == 2 Yards: " + q11.equals(q12)); // true
    }
}