import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TemperatureTest {

    @Test
    void testCelsiusToFahrenheit() {

        Quantity<TemperatureUnit> t =
                new Quantity<>(0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> result =
                t.convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(32.0, result.getValue());
    }

    @Test
    void testEquality() {

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(100, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(212, TemperatureUnit.FAHRENHEIT);

        assertTrue(t1.equals(t2));
    }

    @Test
    void testUnsupportedOperation() {

        Quantity<TemperatureUnit> t =
                new Quantity<>(100, TemperatureUnit.CELSIUS);

        assertThrows(
            UnsupportedOperationException.class,
            () -> t.add(new Quantity<>(10, TemperatureUnit.CELSIUS))
        );
    }
}