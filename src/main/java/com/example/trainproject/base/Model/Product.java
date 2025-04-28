package com.example.trainproject.base.Model;


import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

  @Id
  private UUID id;
  private String name;
  private Double price;

}
