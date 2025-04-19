package com.example.trainproject;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Util.Wapper.DataTransferObject;
import com.example.trainproject.base.Util.Wapper.TransferTypeRegistry;
import com.example.trainproject.base.Util.Wapper.TransferWrapper;
import com.example.trainproject.base.Util.Wapper.TransferWrapperSerializer;
import com.example.trainproject.base.Util.Wapper.TransferWrapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

public class WrapperTest {

  KafkaTemplate<String,String> kafkaTemplate = mock(KafkaTemplate.class);
  @Value("${my.kafka.topic}")
  String topic = "${my.kafka.topic}";

  ObjectMapper objectMapper;

  Faker faker = new Faker();

  @BeforeEach
  void setUp() {
    Card card = new Card();
    TransferTypeRegistry.register("com.example.trainproject.base.Model.Card",card.getClass());
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  }

  @Test
  public void testKafkaProducer() throws JsonProcessingException {
    Card card = cardCreating();
    TransferWrapper<Card> wrapper = new TransferWrapper<>(
        card,
        "user-service",
        "notification-service"
    );
    TransferWrapperSerializer serializer = new TransferWrapperSerializer(objectMapper);
    String serializedWrapper = serializer.serialize(wrapper);

    kafkaTemplate.send(topic, serializedWrapper);

    verify(kafkaTemplate, times(1)).send(eq(topic), eq(serializedWrapper));
  }

  private Card cardCreating() {
    Card card = new Card();
    card.setCardNumber(faker.number().digits(10).toString());
    card.setFirstName(faker.name().firstName());
    card.setLastName(faker.name().lastName());
    card.setPin1(faker.number().digits(10).toString());
    card.setPin2(faker.number().digits(10).toString());
    return card;
  }


  @KafkaListener(topics = "${my.kafka.topic}", groupId = "group-id")
  public void consume(String messageJson) throws Exception {
    TransferWrapper<?> wrapper = TransferWrapperUtil.fromJsonSafely(messageJson);

    Card card = (Card) wrapper.getData();
    System.out.println("Received card: " + card);
    System.out.println("Received card : " + card.getCardNumber());

    assertEquals("123", card.getId());
    assertEquals("Alice", card.getFirstName());
    assertEquals("sourceProject", wrapper.getSourceProject());
    assertEquals("destinationProject", wrapper.getDestinationProject());
  }


  @Test
  void testSerializationAndDeserialization() throws Exception {
    Card card = new Card();
    card.setCardNumber(faker.number().digits(10).toString());
    card.setFirstName("Alice");
    card.setLastName(faker.name().lastName());
    card.setPin1(faker.number().digits(10).toString());
    card.setPin2(faker.number().digits(10).toString());

    // Wrap the Card object into TransferWrapper
    TransferWrapper<Card> wrapper = TransferWrapper.of(card, "A", "B");

    // Serialize the wrapper into JSON
    String json = TransferWrapperUtil.toJson(wrapper);

    // Register the DTO class for safe deserialization
    TransferTypeRegistry.register(Card.class.getName(), Card.class);

    // Deserialize the JSON back into TransferWrapper
    TransferWrapper<? extends DataTransferObject> result = TransferWrapperUtil.fromJsonSafely(json);

    assertNotNull(result);
    assertTrue(result.getData() instanceof Card);
    Card deserializedCard = (Card) result.getData();

    assertEquals("Alice", deserializedCard.getFirstName());
    assertEquals("A", result.getSourceProject());
    assertEquals("B", result.getDestinationProject());
  }

  @Test
  void testSerializationAndDeserialization2() throws Exception {
    Card card = new Card();
    card.setCardNumber(faker.number().digits(10).toString());
    card.setFirstName("Alice");
    card.setLastName(faker.name().lastName());
    card.setPin1(faker.number().digits(10).toString());
    card.setPin2(faker.number().digits(10).toString());

    // Wrap the Card object into TransferWrapper
    TransferWrapper<Card> wrapper = TransferWrapper.of(card, "A", "B");

    // Serialize the wrapper into JSON
    String json = TransferWrapperUtil.toJson(wrapper);

    // Register the DTO class for safe deserialization
    TransferTypeRegistry.register(wrapper.getData().getClass().getTypeName(), wrapper.getData().getClass());

    // Deserialize the JSON back into TransferWrapper
    TransferWrapper<? extends DataTransferObject> result = TransferWrapperUtil.fromJsonSafely(json);

    assertNotNull(result);
    assertTrue(result.getData() instanceof Card);
    Card deserializedCard = (Card) result.getData();

    assertEquals("Alice", deserializedCard.getFirstName());
    assertEquals("A", result.getSourceProject());
    assertEquals("B", result.getDestinationProject());
  }

}
