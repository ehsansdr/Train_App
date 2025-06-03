package com.example.trainproject.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "operational_division")
public class OperationalDivision {
  @Id
  private String id;
}
