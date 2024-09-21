package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CurvePoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @NotNull(message="Curve Id is mandatory")
    @Column(nullable = false)
    private Integer curveId;

    @Column
    private Timestamp asOfDate;

    @NotNull(message="Term is mandatory")
    @Positive(message="Term must be positive")
    @Column
    private Double term;


    @NotNull(message="Value is mandatory")
    @Positive(message="Value must be positive")
    @Column
    private Double value;

    @Column
    private Timestamp creationDate;
}
