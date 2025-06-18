package com.example.trainproject.Model;

import com.example.trainproject.callBack.Auditable;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "credit_score")
public class CreditScore implements Serializable, Auditable {

  @Id
  @Field(type = FieldType.Keyword)
  private String id;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime createdAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime updatedAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime deletedAt;

}
