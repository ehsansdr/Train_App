package com.example.trainproject.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "user_cal_log")
public class UserCallLog {

  @Id
  private String id;

}
