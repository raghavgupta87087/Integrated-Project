package QuantityMeasurementApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MeasurementApplicationTests {

    // ✅ Feet Tests
    

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
        Assertions.assertTrue(f1.equals(f1));  // ✅ FIXED
    }

    // ✅ Inches Tests (IMPORTANT for UC2)

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
}