public interface IMeasurable {

    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);

    /* =========================
       UC14 ADDITION
       ========================= */

    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    default SupportsArithmetic supportsArithmetic() {
        return () -> true; // default for Length, Weight, Volume
    }

    default void validateOperationSupport(String operation) {
        // default: do nothing
    }
}