package com.example.trainproject.base.Mapper;

import com.example.trainproject.base.Dto.CardResponse;
import com.example.trainproject.base.Model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardMapper {

  public CardResponse mapToResponse(Card card) {
    if (card == null) {
      return null;
    }
    CardResponse cardResponce = new CardResponse();
    cardResponce.setCardNumber(card.getCardNumber());
    cardResponce.setStatus(card.getStatus());
    return cardResponce;
  }
}
