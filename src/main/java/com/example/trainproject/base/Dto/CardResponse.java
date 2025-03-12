package com.example.trainproject.base.Dto;

import com.example.trainproject.base.Constant.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardResponse {

  private String firstName;
  private String lastName;
  private String cardNumber;
  private CardStatus status;

}
