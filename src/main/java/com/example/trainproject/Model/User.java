package com.example.trainproject.Model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "user")
public class User {

  @Id
  private String id;

  private String name;
  private String surname;
  private String email;


}
