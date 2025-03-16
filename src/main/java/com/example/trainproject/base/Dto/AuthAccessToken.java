package com.example.trainproject.base.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthAccessToken {

  private String bearerToken;
  private String refreshToken;
}
