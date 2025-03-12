package com.example.trainproject.base.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CardCreateResponce {

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

}
