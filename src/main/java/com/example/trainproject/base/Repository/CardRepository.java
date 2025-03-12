package com.example.trainproject.base.Repository;

import com.example.trainproject.base.Model.Card;
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
            from Card c
      where 
            
      c.deletedAt != null
      """)
  Page<Card> findAllExistedCards(Pageable pageable);

}
