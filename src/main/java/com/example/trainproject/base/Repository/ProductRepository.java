package com.example.trainproject.base.Repository;


import com.example.trainproject.base.Model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, UUID> {

  Optional<Product> findByName(String name);


  @Query("{\"bool\": {\"must\": [{\"match\": {\"category\": \"?0\"}}, {\"range\": {\"price\": {\"lte\": \"?1\"}}}]}}")
  List<Product> findByCategoryAndPriceLessThanEqual(String category, Double price);
}
