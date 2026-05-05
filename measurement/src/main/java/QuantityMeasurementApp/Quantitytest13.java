import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityTestUC13 {

    @Test
    void testSubtractDelegation() {

        Quantity<LengthUnit> a =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(5, LengthUnit.FEET);

        assertEquals(5.0, a.subtract(b).getValue());
    }

    @Test
    void testDivideDelegation() {

        Quantity<LengthUnit> a =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(2, LengthUnit.FEET);

        assertEquals(5.0, a.divide(b));
    }

    @Test
    void testValidationSameAcrossAll() {

        Quantity<LengthUnit> a =
                new Quantity<>(10, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> a.subtract(null));

        assertThrows(IllegalArgumentException.class,
                () -> a.divide(null));
    }
}