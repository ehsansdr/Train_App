package com.example.trainproject.Model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "district")
public class District implements Serializable {

  @Id
  @Field(type = FieldType.Keyword)
  private String id;

  @NonNull
  private City city;
  @NonNull
  private String districtName;


}
