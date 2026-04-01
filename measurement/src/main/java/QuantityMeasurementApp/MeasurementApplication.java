package QuantityMeasurementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class MeasurementApplication {

    // Static Feet class
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ✅ FIXED: Make Inches static
    static class Inches {
        private double value;

        public Inches(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (obj == null || !(obj instanceof Inches)) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ✅ Separate methods (as required in UC2)
    public static boolean compareFeet(double val1, double val2) {
        Feet f1 = new Feet(val1);
        Feet f2 = new Feet(val2);
        return f1.equals(f2);
    }

    public static boolean compareInches(double val1, double val2) {
        Inches i1 = new Inches(val1);
        Inches i2 = new Inches(val2);
        return i1.equals(i2);
    }

 public static void main(String[] args) {

    // ===== UC1: Feet =====
    System.out.println("---- UC1: Feet ----");
    System.out.println("Feet Equal: " + compareFeet(34.5, 34.5));

    // ===== UC2: Inches =====
    System.out.println("\n---- UC2: Inches ----");
    System.out.println("Inches Equal: " + compareInches(10.0, 10.0));

    // ===== UC3: QuantityLength =====
    System.out.println("\n---- UC3: Generic Quantity ----");

    QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
    QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

    System.out.println("Are equal? " + q1.equals(q2)); // true

    QuantityLength q3 = new QuantityLength(2.0, LengthUnit.FEET);

    System.out.println("Are equal? " + q1.equals(q3)); // false
}



}