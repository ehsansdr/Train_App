package com.example.trainproject.base.Repository;

import com.example.trainproject.base.Model.Transaction;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  Page<Transaction> findAll(Pageable pageable);



  Optional<Transaction> findById(UUID transactionId);
}
