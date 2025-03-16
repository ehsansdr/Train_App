package com.example.trainproject.base.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginUserResponse {

  private String user;
  private String bearerToken;
  private String refreshToken;
  private long expiresInSeconds;
}
