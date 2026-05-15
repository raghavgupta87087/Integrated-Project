package com.app.quantitymeasurement17;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/* =========================================================
   UC17 - SPRING BOOT SINGLE FILE IMPLEMENTATION
   Save as:
   QuantityMeasurementAppApplication.java
   ========================================================= */

@SpringBootApplication
public class QuantityMeasurementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                QuantityMeasurementAppApplication.class,
                args
        );
    }

    /* =========================================================
       ENUM
       ========================================================= */

    enum LengthUnit {

        FEET(1.0),

        INCHES(1.0 / 12.0),

        YARDS(3.0),

        CENTIMETERS(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    /* =========================================================
       QUANTITY CLASS
       ========================================================= */

    static class Quantity {

        private final double value;
        private final LengthUnit unit;

        public Quantity(
                double value,
                LengthUnit unit
        ) {
            this.value = value;
            this.unit = unit;
        }

        public double convertToFeet() {
            return unit.toFeet(value);
        }

        public Quantity add(Quantity other) {

            double result =
                    this.convertToFeet()
                            + other.convertToFeet();

            return new Quantity(
                    result,
                    LengthUnit.FEET
            );
        }

        public boolean compare(Quantity other) {

            return Math.abs(
                    this.convertToFeet()
                            - other.convertToFeet()
            ) < 0.0001;
        }

        public Quantity convertTo(
                LengthUnit targetUnit
        ) {

            double feet =
                    this.convertToFeet();

            double result =
                    feet / targetUnit.toFeet(1);

            return new Quantity(
                    result,
                    targetUnit
            );
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }
    }

    /* =========================================================
       JPA ENTITY
       ========================================================= */

    @Entity
    @Table(name = "quantity_measurements")
    static class QuantityMeasurementEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String operation;

        private String resultValue;

        private LocalDateTime createdAt;

        public QuantityMeasurementEntity() {
        }

        public QuantityMeasurementEntity(
                String operation,
                String resultValue
        ) {
            this.operation = operation;
            this.resultValue = resultValue;
        }

        @PrePersist
        public void prePersist() {
            createdAt = LocalDateTime.now();
        }

        public Long getId() {
            return id;
        }

        public String getOperation() {
            return operation;
        }

        public String getResultValue() {
            return resultValue;
        }
    }

    /* =========================================================
       DTO
       ========================================================= */

    static class QuantityDTO {

        @NotNull
        private Double value;

        @Pattern(
                regexp =
                        "FEET|INCHES|YARDS|CENTIMETERS"
        )
        private String unit;

        public QuantityDTO() {
        }

        public QuantityDTO(
                Double value,
                String unit
        ) {
            this.value = value;
            this.unit = unit;
        }

        public Double getValue() {
            return value;
        }

        public String getUnit() {
            return unit;
        }
    }

    /* =========================================================
       REPOSITORY
       ========================================================= */

    @Repository
    interface QuantityMeasurementRepository
            extends JpaRepository<
            QuantityMeasurementEntity,
            Long> {

        List<QuantityMeasurementEntity>
        findByOperation(
                String operation
        );
    }

    /* =========================================================
       SERVICE
       ========================================================= */

    @Service
    static class QuantityMeasurementService {

        @Autowired
        private QuantityMeasurementRepository repository;

        public boolean compare(
                QuantityDTO q1,
                QuantityDTO q2
        ) {

            Quantity a =
                    new Quantity(
                            q1.getValue(),
                            LengthUnit.valueOf(
                                    q1.getUnit()
                            )
                    );

            Quantity b =
                    new Quantity(
                            q2.getValue(),
                            LengthUnit.valueOf(
                                    q2.getUnit()
                            )
                    );

            boolean result =
                    a.compare(b);

            repository.save(
                    new QuantityMeasurementEntity(
                            "COMPARE",
                            String.valueOf(result)
                    )
            );

            return result;
        }

        public QuantityDTO add(
                QuantityDTO q1,
                QuantityDTO q2
        ) {

            Quantity a =
                    new Quantity(
                            q1.getValue(),
                            LengthUnit.valueOf(
                                    q1.getUnit()
                            )
                    );

            Quantity b =
                    new Quantity(
                            q2.getValue(),
                            LengthUnit.valueOf(
                                    q2.getUnit()
                            )
                    );

            Quantity result =
                    a.add(b);

            repository.save(
                    new QuantityMeasurementEntity(
                            "ADD",
                            result.getValue()
                                    + " "
                                    + result.getUnit()
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    result.getUnit().name()
            );
        }

        public QuantityDTO convert(
                QuantityDTO q1,
                String target
        ) {

            Quantity q =
                    new Quantity(
                            q1.getValue(),
                            LengthUnit.valueOf(
                                    q1.getUnit()
                            )
                    );

            Quantity result =
                    q.convertTo(
                            LengthUnit.valueOf(target)
                    );

            repository.save(
                    new QuantityMeasurementEntity(
                            "CONVERT",
                            result.getValue()
                                    + " "
                                    + result.getUnit()
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    result.getUnit().name()
            );
        }

        public List<QuantityMeasurementEntity>
        getHistory(
                String operation
        ) {

            return repository.findByOperation(
                    operation
            );
        }
    }

    /* =========================================================
       REST CONTROLLER
       ========================================================= */

    @RestController
    @RequestMapping(
            "/api/v1/quantities"
    )
    static class QuantityMeasurementController {

        @Autowired
        private QuantityMeasurementService service;

        @PostMapping("/compare")
        public ResponseEntity<Boolean>
        performCompare(
                @RequestBody QuantityInput input
        ) {

            return ResponseEntity.ok(
                    service.compare(
                            input.thisQuantityDTO,
                            input.thatQuantityDTO
                    )
            );
        }

        @PostMapping("/add")
        public ResponseEntity<QuantityDTO>
        performAdd(
                @RequestBody QuantityInput input
        ) {

            return ResponseEntity.ok(
                    service.add(
                            input.thisQuantityDTO,
                            input.thatQuantityDTO
                    )
            );
        }

        @PostMapping("/convert")
        public ResponseEntity<QuantityDTO>
        performConvert(
                @RequestBody QuantityInput input
        ) {

            return ResponseEntity.ok(
                    service.convert(
                            input.thisQuantityDTO,
                            input.thatQuantityDTO.getUnit()
                    )
            );
        }

        @GetMapping(
                "/history/{operation}"
        )
        public ResponseEntity<
                List<QuantityMeasurementEntity>
                > history(
                @PathVariable String operation
        ) {

            return ResponseEntity.ok(
                    service.getHistory(
                            operation
                    )
            );
        }
    }

    /* =========================================================
       INPUT DTO
       ========================================================= */

    static class QuantityInput {

        public QuantityDTO thisQuantityDTO;

        public QuantityDTO thatQuantityDTO;
    }

    /* =========================================================
       GLOBAL EXCEPTION HANDLER
       ========================================================= */

    @ControllerAdvice
    static class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String>
        handleException(
                Exception e
        ) {

            return ResponseEntity
                    .status(
                            HttpStatus.BAD_REQUEST
                    )
                    .body(
                            e.getMessage()
                    );
        }
    }
}