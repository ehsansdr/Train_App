package com.example.trainproject.base.Service;

import com.example.trainproject.base.Model.User;
import com.example.trainproject.base.Repository.CardRepository;
import com.example.trainproject.base.Repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User getSingle(UUID uuid) {
    return userRepository.findFirstById(uuid);
  }
}
