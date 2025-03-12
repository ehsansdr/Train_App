package com.example.trainproject.base.Repository;

import com.example.trainproject.base.Model.Card;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

  @Query("""
    SELECT c 
    FROM Card c
    WHERE c.deletedAt IS NOT NULL
    """)
  Page<Card> findAllExistedCards(Pageable pageable);



  @Query(value = "SELECT nextval('card_seq')", nativeQuery = true)
  BigDecimal getSequence();

}
