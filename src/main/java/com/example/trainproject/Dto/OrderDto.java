package com.example.trainproject.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderDto {

    @NotNull
    private String userName;

    @NotNull
    @Positive
    private double totalAmount;

}
