package com.example.trainproject.base.Dto;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class LoginUserRequest implements Serializable {

  @NotEmpty()
  String username;

  @NotEmpty
  String password;
}
