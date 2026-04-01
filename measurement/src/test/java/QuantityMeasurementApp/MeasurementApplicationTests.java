package QuantityMeasurementApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MeasurementApplicationTests {

    // ================= UC1: Feet =================

    @Test
    void testFeetEquality_SameValue() {
        MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
        MeasurementApplication.Feet f2 = new MeasurementApplication.Feet(89.6);
        Assertions.assertEquals(f1, f2);
    }

    @Test
    void testFeetInEquality_DifferentValue() {
        MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
        MeasurementApplication.Feet f2 = new MeasurementApplication.Feet(34.6);
        Assertions.assertNotEquals(f1, f2);
    }

    @Test
    void testFeetEquality_NullComparison() {
        MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(55.0);
        Assertions.assertFalse(f1.equals(null));
    }

    @Test
    void testFeetEquality_DifferentType() {
        MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.0);
        Assertions.assertFalse(f1.equals("Some String"));
    }

    @Test
    void testFeetEquality_SameReference() {
        MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.0);
        Assertions.assertTrue(f1.equals(f1));
    }

    // ================= UC2: Inches =================

    @Test
    void testInchesEquality_SameValue() {
        MeasurementApplication.Inches i1 = new MeasurementApplication.Inches(10.0);
        MeasurementApplication.Inches i2 = new MeasurementApplication.Inches(10.0);
        Assertions.assertEquals(i1, i2);
    }

    @Test
    void testInchesInEquality_DifferentValue() {
        MeasurementApplication.Inches i1 = new MeasurementApplication.Inches(10.0);
        MeasurementApplication.Inches i2 = new MeasurementApplication.Inches(20.0);
        Assertions.assertNotEquals(i1, i2);
    }

    @Test
    void testInchesEquality_NullComparison() {
        MeasurementApplication.Inches i1 = new MeasurementApplication.Inches(10.0);
        Assertions.assertFalse(i1.equals(null));
    }

    @Test
    void testInchesEquality_DifferentType() {
        MeasurementApplication.Inches i1 = new MeasurementApplication.Inches(10.0);
        Assertions.assertFalse(i1.equals(100));
    }

    @Test
    void testInchesEquality_SameReference() {
        MeasurementApplication.Inches i1 = new MeasurementApplication.Inches(10.0);
        Assertions.assertTrue(i1.equals(i1));
    }

    // ================= UC3: QuantityLength =================

    @Test
    void testEquality_FeetToFeet_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        Assertions.assertEquals(q1, q2);
    }

    @Test
    void testEquality_InchToInch_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCHES);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCHES);

        Assertions.assertEquals(q1, q2);
    }

    @Test
    void testEquality_FeetToInch_EquivalentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        Assertions.assertEquals(q1, q2);
    }

    @Test
    void testEquality_InchToFeet_EquivalentValue() {
        QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        Assertions.assertEquals(q1, q2);
    }

    @Test
    void testEquality_FeetToFeet_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);

        Assertions.assertNotEquals(q1, q2);
    }

    @Test
    void testEquality_InchToInch_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCHES);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.INCHES);

        Assertions.assertNotEquals(q1, q2);
    }

    @Test
    void testEquality_SameReference() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        Assertions.assertTrue(q1.equals(q1));
    }

    @Test
    void testEquality_NullComparison() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        Assertions.assertFalse(q1.equals(null));
    }

    @Test
    void testEquality_NullUnit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(1.0, null);
        });
    }

    // ================= UC4: Extended Units =================

    @Test
    void testEquality_YardToFeet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        Assertions.assertEquals(q1, q2);
    }

    @Test
    void testEquality_YardToInches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCHES);

        Assertions.assertEquals(q1, q2);
    }

    @Test
    void testEquality_CmToInches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(0.393701, LengthUnit.INCHES);

        Assertions.assertEquals(q1, q2);
    }

    @Test
    void testEquality_CmToFeet_NotEqual() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        Assertions.assertNotEquals(q1, q2);
    }
}