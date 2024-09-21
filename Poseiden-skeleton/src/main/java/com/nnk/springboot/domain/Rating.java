package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @NotBlank(message="Moodys Rating is mandatory")
    @Column
    private String moodysRating;

    @NotBlank(message="Sand P Rating is mandatory")
    @Column
    private String sandPRating;

    @NotBlank(message="Fitch Rating is mandatory")
    @Column
    private String fitchRating;

    @NotNull(message="Order Number is mandatory")
    @Positive(message="Order Number must be positive")
    @Column
    private Integer orderNumber;
}
