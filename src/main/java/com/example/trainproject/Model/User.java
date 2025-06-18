package com.example.trainproject.Model;


import com.example.trainproject.callBack.Auditable;
import com.example.trainproject.constants.Gender;
import com.example.trainproject.constants.KycStep;
import com.example.trainproject.constants.Role;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user")
public class User implements Serializable, Auditable {

  @Id
  @Field(type = FieldType.Keyword)
  private String id;

  @Field(type = FieldType.Keyword, name = "national_code")
  private String nationalCode;

  // MultiField allows searching by parts of the name (text) AND sorting/aggregating on the exact name (keyword)
  @MultiField(
      mainField = @Field(type = FieldType.Text, fielddata = true, name = "first_name_fa"), // For full-text search
      otherFields = {
          @InnerField(suffix = "keyword", type = FieldType.Keyword) // For sorting and exact filtering
      }
  )
  private String firstNameFa;

  @MultiField(
      mainField = @Field(type = FieldType.Text, fielddata = true), // For full-text search
      otherFields = {
          @InnerField(suffix = "keyword", type = FieldType.Keyword) // For sorting and exact filtering
      }
  )
  private String lastNameFa;

  @Field(type = FieldType.Keyword, name = "role")
  private Role role;

  @Field(type = FieldType.Keyword, name = "gender")
  private Gender gender;

  @Field(type = FieldType.Keyword, name = "phone_number")
  private String phoneNumber;

  @Field(type = FieldType.Date, format = DateFormat.date, name = "birth_date")
  private LocalDate birthDate;

  @Field(type = FieldType.Date, format = DateFormat.date_time, name = "registered_at")
  private ZonedDateTime registeredAt;

  @Field(type = FieldType.Keyword, name = "kyc_step")
  private KycStep kycStep;

  @Field(type = FieldType.Long, name = "total_cleared_orders")
  private Long totalClearedOrders;

  @Field(type = FieldType.Date, format = DateFormat.date_time, name = "last_follow_up_date")
  private ZonedDateTime lastFollowUpDate;

  @Field(type = FieldType.Date, format = DateFormat.date_time, name = "created_at")
  private ZonedDateTime createdAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time, name = "updated_at")
  private ZonedDateTime updatedAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time, name = "deleted_at")
  private ZonedDateTime deletedAt;

}
