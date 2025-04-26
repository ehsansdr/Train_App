package com.example.trainproject.base.Repository;

import com.example.trainproject.base.Model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> findByUsername(String username);
  User findFirstById(UUID uuid);
  Optional<User> findById(UUID userId);
}
