package com.example.trainproject.Model;

import com.example.trainproject.callBack.Auditable;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "city")
public class City implements Serializable, Auditable {


  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime createdAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime updatedAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime deletedAt;
}
