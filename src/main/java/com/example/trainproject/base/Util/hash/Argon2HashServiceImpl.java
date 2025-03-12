package com.example.trainproject.base.Util.hash;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Argon2HashServiceImpl implements Argon2HashService {

  private final Argon2PasswordEncoder argon2PasswordEncoder;

  public Argon2HashServiceImpl() {
    // Customize Argon2 parameters
    int saltLength = 16;     // salt length in bytes
    int hashLength = 32;     // hash length in bytes
    int parallelism = 2;     // parallelism level
    int memoryCost = 65536;  // memory cost in KB (64MB)
    int iterations = 3;      // iterations

    this.argon2PasswordEncoder = new Argon2PasswordEncoder(saltLength, hashLength, parallelism,
        memoryCost, iterations);
  }

  @Override
  public String hashValue(String value) {
    return argon2PasswordEncoder.encode(value);
  }

  @Override
  public boolean verifyValue(String rawValue, String encodedValue) {
    return argon2PasswordEncoder.matches(rawValue, encodedValue);
  }

}
