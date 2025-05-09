package com.example.trainproject.base.Model;

import com.example.trainproject.base.Constant.CardStatus;
import com.example.trainproject.base.Util.Wapper.DataTransferObject;
import com.example.trainproject.base.Util.Wapper.TransferWrapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Card implements Serializable , DataTransferObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq_gen")
  @SequenceGenerator(name = "card_seq_gen", sequenceName = "card_seq", allocationSize = 1)
  private Long id;


  @Column(name = "card_number")
  @NotNull
  private String cardNumber;


  @Column(name = "pin1", columnDefinition = "VARCHAR(4)")
  @NotNull
  private String pin1;

  @Column(name = "pin2", columnDefinition = "VARCHAR(22)")
  @NotNull
  private String pin2;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(columnDefinition = "VARCHAR(20)", nullable = false)
  @Enumerated(EnumType.STRING)
  private CardStatus status;

  @CreationTimestamp
  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;

  @Column(name = "deleted_at")
  private ZonedDateTime deletedAt;

  public Card(Long id) {
    this.id = id;
  }

}
