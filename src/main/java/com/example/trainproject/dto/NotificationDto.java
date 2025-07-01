package com.example.trainproject.dto;

import com.example.trainproject.constant.Channel;
import com.example.trainproject.constant.NotificationTopic;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDto {

  private UUID receiverId;

  private String contactInfo;

  private Channel channel;

  private NotificationTopic notificationTopic;

  private String messageBody;

  private ZonedDateTime deliveryDate;

  private ZonedDateTime expiryDate;

}
