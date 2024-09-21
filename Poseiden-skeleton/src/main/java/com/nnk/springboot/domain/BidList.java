package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "BidList")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer bidListId;

    @NotBlank(message="Account is mandatory")
    @Column(nullable = false, unique = true)
    private String account;

    @NotBlank(message="Type is mandatory")
    @Column
    private String type;

    @NotNull(message="Bid Quantity can't be null")
    @Column
    private Double bidQuantity;

    @Column
    private Double askQuantity;

    @Column
    private Double bid;

    @Column
    private Double ask;

    @Column
    private String benchmark;

    @Column
    private Timestamp bidListDate;

    @Column
    private String commentary;

    @Column
    private String security;

    @Column
    private String status;

    @Column
    private String trader;

    @Column
    private String book;

    @Column
    private String creationName;

    @Column
    private Timestamp creationDate;

    @Column
    private String revisionName;

    @Column
    private Timestamp revisionDate;

    @Column
    private String dealName;

    @Column
    private String dealType;

    @Column
    private String sourceListId;

    @Column
    private String side;

}
