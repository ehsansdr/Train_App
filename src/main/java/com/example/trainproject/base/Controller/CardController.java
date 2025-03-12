package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Dto.CardCreateResponce;
import com.example.trainproject.base.Dto.CardResponse;
import com.example.trainproject.base.Dto.GenericPaginatedResponse;
import com.example.trainproject.base.Mapper.CardMapper;
import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Service.CardService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

  private final CardService cardService;
  private final CardMapper cardMapper;

  @GetMapping("/{pageSize}/{pageNumber}")
  public GenericPaginatedResponse<List<CardResponse>> getAllCards(
      @PathVariable int pageSize,
      @PathVariable int pageNumber,
      @RequestParam(required = false) String sort
  ) {
    Page<Card> page = cardService.getAllCards(pageSize, pageNumber, sort);
    List<CardResponse> cards = page.stream().map(cardMapper::mapToResponse)
        .toList();

    return GenericPaginatedResponse.success(cards,
        pageNumber,
        pageSize,
        page.getTotalElements());
  }

  @PostMapping("/create-card-request/{userId}")
  public CardResponse createCardRequest(
      @PathVariable UUID id,
      @RequestBody CardCreateResponce cardCreateRequest
  ) throws Exception {
    Card newCard = cardService.createNewCard(id, cardCreateRequest);
    return cardMapper.mapToResponse(newCard);
  }

}
