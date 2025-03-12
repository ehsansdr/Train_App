package com.example.trainproject.base.Service;

import com.example.trainproject.base.Dto.CardCreateResponce;
import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Model.User;
import com.example.trainproject.base.Repository.CardRepository;
import com.example.trainproject.base.Repository.UserRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;
  private final UserService userService;

  public Page<Card> getAllCards(int pageSize,
      int pageNumber,
      String sort) {

    Pageable pageable;
    // sorting part
    if (sort != null) {
      Sort sortOrder = Sort.by(sort.split(",")[0]).ascending();
      if (sort.endsWith("desc")) {
        sortOrder = sortOrder.descending();
      }
      pageable = PageRequest.of(pageNumber, pageSize, sortOrder);
    } else {
      Sort sortOrder = Sort.by("createdAt").descending();
      pageable = PageRequest.of(pageNumber, pageSize, sortOrder);
      pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
    }

    return cardRepository.findAllExistedCards(pageable);
  }

  public Card createNewCard(UUID userId,CardCreateResponce cardCreateRequest) {
    User user = userService.getSingle(userId);
    return null;
  }
}
