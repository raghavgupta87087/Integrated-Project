public class QuantityMeasurementApp16 {

    /* =========================
       IMeasurable
       ========================= */

    interface IMeasurable {
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);

        default void validateOperationSupport(String operation) {
        }
    }

    /* =========================
       LengthUnit
       ========================= */

    enum LengthUnit implements IMeasurable {

        FEET {
            public double convertToBaseUnit(double value) {
                return value;
            }

            public double convertFromBaseUnit(double baseValue) {
                return baseValue;
            }
        },

        INCHES {
            public double convertToBaseUnit(double value) {
                return value / 12.0;
            }

            public double convertFromBaseUnit(double baseValue) {
                return baseValue * 12.0;
            }
        }
    }

    /* =========================
       TemperatureUnit
       ========================= */

    enum TemperatureUnit implements IMeasurable {

        CELSIUS {
            public double convertToBaseUnit(double value) {
                return value;
            }

            public double convertFromBaseUnit(double baseValue) {
                return baseValue;
            }
        },

        FAHRENHEIT {
            public double convertToBaseUnit(double value) {
                return (value - 32) * 5 / 9.0;
            }

            public double convertFromBaseUnit(double baseValue) {
                return (baseValue * 9 / 5.0) + 32;
            }
        };

        @Override
        public void validateOperationSupport(String operation) {
            throw new UnsupportedOperationException(
                    "Temperature does not support " + operation
            );
        }
    }

    /* =========================
       Quantity Class
       ========================= */

    static class Quantity<U extends Enum<U> & IMeasurable> {

        private final double value;
        private final U unit;

        public Quantity(double value, U unit) {
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public U getUnit() {
            return unit;
        }

        public Quantity<U> convertTo(U targetUnit) {

            double base = unit.convertToBaseUnit(value);

            double result =
                    targetUnit.convertFromBaseUnit(base);

            return new Quantity<>(result, targetUnit);
        }

        public Quantity<U> add(Quantity<U> other) {

            unit.validateOperationSupport("ADD");

            double result =
                    unit.convertToBaseUnit(value)
                            + other.unit.convertToBaseUnit(other.value);

            return new Quantity<>(
                    unit.convertFromBaseUnit(result),
                    unit
            );
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    /* =========================
       DTO
       ========================= */

    static class QuantityDTO {

        double value;
        String unit;
        boolean error;
        String message;

        public QuantityDTO(double value, String unit) {
            this.value = value;
            this.unit = unit;
        }

        public static QuantityDTO error(String msg) {

            QuantityDTO dto =
                    new QuantityDTO(0, "");

            dto.error = true;
            dto.message = msg;

            return dto;
        }
    }

    /* =========================
       Repository Layer
       ========================= */

    interface IQuantityMeasurementRepository {
        void save(String data);
    }

    static class QuantityMeasurementCacheRepository
            implements IQuantityMeasurementRepository {

        private static final
        QuantityMeasurementCacheRepository INSTANCE =
                new QuantityMeasurementCacheRepository();

        private QuantityMeasurementCacheRepository() {
        }

        public static QuantityMeasurementCacheRepository
        getInstance() {
            return INSTANCE;
        }

        @Override
        public void save(String data) {
            System.out.println("Saved: " + data);
        }
    }

    /* =========================
       Service Layer
       ========================= */

    interface IQuantityMeasurementService {

        QuantityDTO add(
                QuantityDTO q1,
                QuantityDTO q2
        );

        QuantityDTO convert(
                QuantityDTO q1,
                String targetUnit
        );
    }

    static class QuantityMeasurementServiceImpl
            implements IQuantityMeasurementService {

        private final
        IQuantityMeasurementRepository repository;

        public QuantityMeasurementServiceImpl(
                IQuantityMeasurementRepository repository
        ) {
            this.repository = repository;
        }

        @Override
        public QuantityDTO add(
                QuantityDTO q1,
                QuantityDTO q2
        ) {

            try {

                Quantity<LengthUnit> a =
                        new Quantity<>(
                                q1.value,
                                LengthUnit.valueOf(q1.unit)
                        );

                Quantity<LengthUnit> b =
                        new Quantity<>(
                                q2.value,
                                LengthUnit.valueOf(q2.unit)
                        );

                Quantity<LengthUnit> result =
                        a.add(b);

                repository.save(result.toString());

                return new QuantityDTO(
                        result.getValue(),
                        result.getUnit().name()
                );

            } catch (Exception e) {

                return QuantityDTO.error(
                        e.getMessage()
                );
            }
        }

        @Override
        public QuantityDTO convert(
                QuantityDTO q1,
                String targetUnit
        ) {

            try {

                Quantity<LengthUnit> q =
                        new Quantity<>(
                                q1.value,
                                LengthUnit.valueOf(q1.unit)
                        );

                Quantity<LengthUnit> result =
                        q.convertTo(
                                LengthUnit.valueOf(targetUnit)
                        );

                return new QuantityDTO(
                        result.getValue(),
                        result.getUnit().name()
                );

            } catch (Exception e) {

                return QuantityDTO.error(
                        e.getMessage()
                );
            }
        }
    }

    /* =========================
       Controller Layer
       ========================= */

    static class QuantityMeasurementController {

        private final
        IQuantityMeasurementService service;

        public QuantityMeasurementController(
                IQuantityMeasurementService service
        ) {
            this.service = service;
        }

        public void performAdd(
                QuantityDTO q1,
                QuantityDTO q2
        ) {

            QuantityDTO result =
                    service.add(q1, q2);

            display(result);
        }

        public void performConvert(
                QuantityDTO q1,
                String targetUnit
        ) {

            QuantityDTO result =
                    service.convert(q1, targetUnit);

            display(result);
        }

        private void display(
                QuantityDTO dto
        ) {

            if (dto.error) {

                System.out.println(
                        "Error: " + dto.message
                );

            } else {

                System.out.println(
                        "Result: "
                                + dto.value
                                + " "
                                + dto.unit
                );
            }
        }
    }

    /* =========================
       MAIN METHOD
       ========================= */

    public static void main(String[] args) {

        IQuantityMeasurementRepository repository =
                QuantityMeasurementCacheRepository
                        .getInstance();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(
                        repository
                );

        QuantityMeasurementController controller =
                new QuantityMeasurementController(
                        service
                );

        QuantityDTO q1 =
                new QuantityDTO(
                        1,
                        "FEET"
                );

        QuantityDTO q2 =
                new QuantityDTO(
                        12,
                        "INCHES"
                );

        controller.performAdd(q1, q2);

        controller.performConvert(
                q1,
                "INCHES"
        );
    }
}