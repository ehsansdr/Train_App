package com.example.trainproject.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "TransactionSource")
public class TransactionSource {

  @Id
  private String id;

}
