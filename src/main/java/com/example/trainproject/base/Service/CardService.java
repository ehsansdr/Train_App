package com.example.trainproject.base.Service;

import com.example.trainproject.base.Dto.CardCreateResponce;
import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Model.User;
import com.example.trainproject.base.Repository.CardRepository;
import com.example.trainproject.base.Util.PANgenerator;
import com.example.trainproject.base.Util.hash.Argon2HashService;
import com.example.trainproject.base.Util.hash.Argon2HashServiceImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;
  private final UserService userService;
  private final Argon2HashServiceImpl argon2HashService;

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

  public Card createNewCard(UUID userId,CardCreateResponce cardCreateRequest) throws Exception {
    User user = userService.getSingle(userId);

    Card card = createValidcard(user,cardCreateRequest);
    return card;
  }

  private Card createValidcard(User user, CardCreateResponce cardCreateRequest) throws Exception {
    if (user == null) {
      System.out.println("user is null");
      return null;
    }

    Card card = new Card();
    card.setUser(user);
    card.setCardNumber(PANgenerator.generatePAN(cardRepository.getSequence()));
    card.setPin1(argon2HashService.hashValue("0000"));
    card.setPin2("0000");

    return cardRepository.save(card);
  }
}
