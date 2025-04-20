package com.example.trainproject.base.Service;

import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Util.Wapper.TransferTypeRegistry;
import com.example.trainproject.base.Util.Wapper.TransferWrapper;
import com.example.trainproject.base.Util.Wapper.TransferWrapperKafkaDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@EnableKafka
@Service
public class KafkaConsumerService {

  private final TransferWrapperKafkaDeserializer transferDeserializer;
  private final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

  public KafkaConsumerService(TransferWrapperKafkaDeserializer transferDeserializer) {
    this.transferDeserializer = transferDeserializer;
  }

  @KafkaListener(topics = "${my.kafka.topic}", groupId = "${my.kafka.consumer-group-id}")
  public void consumeMessage(TransferWrapper<?> wrapper) {
    try {
      // Optional: Register the DTO class for further use
      TransferTypeRegistry.register(wrapper.getData().getClass().getTypeName(), wrapper.getData().getClass());

      // Log or process
      System.out.println("Received: " + wrapper.getData());

      // Validate
      wrapper.validate();

      // Optional: route depending on type
      if (wrapper.getData() instanceof Card card) {
        processCard(card);
      }

    } catch (Exception ex) {
      log.error("Invalid transfer message", ex);
    }
  }

  private void processCard(Card card) {
    // Example logic — customize as needed
    System.out.println("Processing card: " + card.getCardNumber());

    // You could call a service to handle business logic
    // cardService.handleNewCard(card);
  }

}
