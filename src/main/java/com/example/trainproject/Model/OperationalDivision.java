package com.example.trainproject.Model;

import java.time.ZonedDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "operational_division")
public class OperationalDivision {
  @Id
  private String id;


  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private ZonedDateTime deletedAt;
}

