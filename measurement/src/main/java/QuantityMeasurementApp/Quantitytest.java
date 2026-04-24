import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Quantitytest {

    @Test
    void testSubtractSameUnit() {

        Quantity<LengthUnit> a =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(5, LengthUnit.FEET);

        Quantity<LengthUnit> result = a.subtract(b);

        assertEquals(
            new Quantity<>(5, LengthUnit.FEET),
            result
        );
    }

    @Test
    void testSubtractCrossUnit() {

        Quantity<LengthUnit> a =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(6, LengthUnit.INCHES);

        Quantity<LengthUnit> result = a.subtract(b);

        assertEquals(
            new Quantity<>(9.5, LengthUnit.FEET),
            result
        );
    }

    @Test
    void testSubtractExplicitTarget() {

        Quantity<LengthUnit> a =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(6, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                a.subtract(b, LengthUnit.INCHES);

        assertEquals(
            new Quantity<>(114, LengthUnit.INCHES),
            result
        );
    }

    @Test
    void testDivideSameUnit() {

        Quantity<LengthUnit> a =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(2, LengthUnit.FEET);

        assertEquals(5.0, a.divide(b));
    }

    @Test
    void testDivideCrossUnit() {

        Quantity<LengthUnit> a =
                new Quantity<>(24, LengthUnit.INCHES);

        Quantity<LengthUnit> b =
                new Quantity<>(2, LengthUnit.FEET);

        assertEquals(1.0, a.divide(b));
    }

    @Test
    void testDivideByZero() {

        Quantity<LengthUnit> a =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(0, LengthUnit.FEET);

        assertThrows(
            ArithmeticException.class,
            () -> a.divide(b)
        );
    }
}