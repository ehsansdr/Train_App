package com.example.trainproject.base.Util.hash;

import org.springframework.stereotype.Service;

public interface Argon2HashService {

  String hashValue(String value);

  boolean verifyValue(String rawValue, String encodedValue);

}
