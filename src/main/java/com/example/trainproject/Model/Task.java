package com.example.trainproject.Model;

import com.example.trainproject.callBack.Auditable;
import com.example.trainproject.constants.CallSatus;
import com.example.trainproject.constants.CallType;
import com.example.trainproject.constants.TaskTopic;
import com.example.trainproject.constants.TaskType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "task")
public class Task implements Serializable, Auditable {

  @Id
  @Field(type = FieldType.Keyword)
  private String id;


  @Field(type = FieldType.Nested, name = "user")
  private User user;


  @Field(type = FieldType.Keyword, name = "call_type")
  private CallType callType;


  @Field(type = FieldType.Keyword, name = "task_type")
  private TaskType taskType;


  @Field(type = FieldType.Keyword, name = "task_topic")
  private TaskTopic taskTopic;


  @Field(type = FieldType.Keyword, name = "call_status")
  private CallSatus callSatus;


  @Field(type = FieldType.Nested, name = "created_by")
  private User createdBy;


  @Field(type = FieldType.Nested, name = "updated_by")
  private User updatedBy;


  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime createdAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime updatedAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private ZonedDateTime deletedAt;

}
