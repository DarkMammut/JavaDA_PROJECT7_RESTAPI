package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Column(nullable = false)
    private Integer curveId;

    @Column
    private Timestamp asOfDate;

    @NotNull(message="Term cannot be null")
    @Positive(message="Term must be positive")
    @NotBlank(message="Term is mandatory")
    @Column
    private Double term;


    @NotNull(message="Value cannot be null")
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
    @NotBlank(message="Value is mandatory")
    @Column
    private Double value;

    @Column
    private Timestamp creationDate;
}
