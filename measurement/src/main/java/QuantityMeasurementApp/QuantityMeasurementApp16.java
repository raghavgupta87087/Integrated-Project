import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* =========================================
   UC16 - JDBC + N-Tier Architecture
   Single File Implementation
   Save as:
   QuantityMeasurementApp.java
   ========================================= */

public class QuantityMeasurementApp16 {

    /* =========================================
       IMeasurable
       ========================================= */

    interface IMeasurable {
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);

        default void validateOperationSupport(String operation) {
        }
    }

    /* =========================================
       LengthUnit
       ========================================= */

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

    /* =========================================
       Quantity Class
       ========================================= */

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

        public Quantity<U> add(Quantity<U> other) {

            double result =
                    unit.convertToBaseUnit(value)
                            + other.unit.convertToBaseUnit(other.value);

            return new Quantity<>(
                    unit.convertFromBaseUnit(result),
                    unit
            );
        }

        public Quantity<U> convertTo(U targetUnit) {

            double base =
                    unit.convertToBaseUnit(value);

            double result =
                    targetUnit.convertFromBaseUnit(base);

            return new Quantity<>(result, targetUnit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    /* =========================================
       DTO
       ========================================= */

    static class QuantityDTO {

        double value;
        String unit;

        boolean error;
        String message;

        public QuantityDTO(
                double value,
                String unit
        ) {
            this.value = value;
            this.unit = unit;
        }

        public static QuantityDTO error(
                String msg
        ) {

            QuantityDTO dto =
                    new QuantityDTO(0, "");

            dto.error = true;
            dto.message = msg;

            return dto;
        }
    }

    /* =========================================
       Database Exception
       ========================================= */

    static class DatabaseException
            extends RuntimeException {

        public DatabaseException(
                String message,
                Throwable cause
        ) {
            super(message, cause);
        }
    }

    /* =========================================
       Connection Pool
       ========================================= */

    static class ConnectionPool {

        private static final String URL =
                "jdbc:h2:mem:testdb";

        private static final String USER =
                "sa";

        private static final String PASSWORD =
                "";

        public static Connection getConnection() {

            try {

                Class.forName("org.h2.Driver");

                return DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );

            } catch (Exception e) {

                throw new DatabaseException(
                        "Connection failed",
                        e
                );
            }
        }
    }

    /* =========================================
       Repository Interface
       ========================================= */

    interface IQuantityMeasurementRepository {

        void save(String operation);

        List<String> getAll();
    }

    /* =========================================
       Database Repository
       ========================================= */

    static class QuantityMeasurementDatabaseRepository
            implements IQuantityMeasurementRepository {

        public QuantityMeasurementDatabaseRepository() {

            createTable();
        }

        private void createTable() {

            String sql =
                    "CREATE TABLE IF NOT EXISTS measurements (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "operation VARCHAR(255)" +
                            ")";

            try (
                    Connection connection =
                            ConnectionPool.getConnection();

                    Statement statement =
                            connection.createStatement()
            ) {

                statement.execute(sql);

            } catch (SQLException e) {

                throw new DatabaseException(
                        "Table creation failed",
                        e
                );
            }
        }

        @Override
        public void save(String operation) {

            String sql =
                    "INSERT INTO measurements(operation) VALUES(?)";

            try (
                    Connection connection =
                            ConnectionPool.getConnection();

                    PreparedStatement preparedStatement =
                            connection.prepareStatement(sql)
            ) {

                preparedStatement.setString(
                        1,
                        operation
                );

                preparedStatement.executeUpdate();

            } catch (SQLException e) {

                throw new DatabaseException(
                        "Save failed",
                        e
                );
            }
        }

        @Override
        public List<String> getAll() {

            List<String> list =
                    new ArrayList<>();

            String sql =
                    "SELECT operation FROM measurements";

            try (
                    Connection connection =
                            ConnectionPool.getConnection();

                    PreparedStatement preparedStatement =
                            connection.prepareStatement(sql);

                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                while (resultSet.next()) {

                    list.add(
                            resultSet.getString("operation")
                    );
                }

            } catch (SQLException e) {

                throw new DatabaseException(
                        "Fetch failed",
                        e
                );
            }

            return list;
        }
    }

    /* =========================================
       Service Interface
       ========================================= */

    interface IQuantityMeasurementService {

        QuantityDTO add(
                QuantityDTO q1,
                QuantityDTO q2
        );

        QuantityDTO convert(
                QuantityDTO q1,
                String targetUnit
        );

        List<String> getSavedOperations();
    }

    /* =========================================
       Service Implementation
       ========================================= */

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

                repository.save(
                        result.toString()
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

                repository.save(
                        result.toString()
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

        @Override
        public List<String> getSavedOperations() {

            return repository.getAll();
        }
    }

    /* =========================================
       Controller
       ========================================= */

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

        public void showDatabaseRecords() {

            List<String> records =
                    service.getSavedOperations();

            System.out.println(
                    "\nDATABASE RECORDS"
            );

            for (String record : records) {

                System.out.println(record);
            }
        }

        private void display(
                QuantityDTO dto
        ) {

            if (dto.error) {

                System.out.println(
                        "Error: "
                                + dto.message
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

    /* =========================================
       MAIN METHOD
       ========================================= */

    public static void main(String[] args) {

        IQuantityMeasurementRepository repository =
                new QuantityMeasurementDatabaseRepository();

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

        controller.showDatabaseRecords();
    }
}