package com.example.trainproject.base.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardCreateResponce {

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

}
