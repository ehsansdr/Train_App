package com.example.trainproject;

import com.example.trainproject.base.Config.MessageConfig;
import com.example.trainproject.base.Constant.Role;
import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Model.KafkaProduceMessage;
import com.example.trainproject.base.Model.Transaction;
import com.example.trainproject.base.Model.User;
import com.example.trainproject.base.Util.Wapper.DataTransferObject;
import com.example.trainproject.base.Util.Wapper.TransferTypeRegistry;
import com.example.trainproject.base.Util.Wapper.TransferWrapper;
import com.example.trainproject.base.Service.KafkaProducerService;
import com.example.trainproject.base.Service.UserService;
import com.example.trainproject.base.Util.Wapper.TransferWrapperSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class TrainProjectApplication {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${my.kafka.topic}")
    String topic = "${my.kafka.topic}";

    // it is important to declare object mapper as it is
    private ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    Faker faker = new Faker();
    private final PasswordEncoder passwordEncoder;

    private MessageConfig messageSource = new MessageConfig();
    public static void main(String[] args) {
        SpringApplication.run(TrainProjectApplication.class, args);
    }

    // @Bean
    public CommandLineRunner init(KafkaProducerService kafkaProducerService) {
        return args -> {
            Faker faker = new Faker();
            KafkaProduceMessage kafkaProducedMessage = new KafkaProduceMessage();
            kafkaProducedMessage.setId(1);
            kafkaProducedMessage.setName(faker.name().name());
            kafkaProducedMessage.setDescription(faker.lorem().paragraph());
            kafkaProducerService.sendMessageJson(kafkaProducedMessage);
            System.out.println(kafkaProducedMessage);
        };
    }

    // @Bean
    public CommandLineRunner userCreation(
        UserService userService
    ) {
        return args -> {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);
            userService.save(user);
        };
    }
    // @Bean
    public CommandLineRunner wrapperClass2(
        UserService userService,
        KafkaProducerService kafkaProducerService
    ) {
        return args -> {
            Faker faker = new Faker();
            Card card = new Card();
            card.setCardNumber(faker.number().digits(10).toString());
            card.setFirstName(faker.name().firstName());
            card.setLastName(faker.name().lastName());
            card.setPin1(faker.number().digits(10).toString());
            card.setPin2(faker.number().digits(10).toString());
            TransferWrapper<Card> wrapper = new TransferWrapper<>(
                card,
                "user-service",
                "notification-service"
            );

            System.out.println("card : " + card.toString());
            System.out.println("************************************************");
            System.out.println( "**** " + wrapper);
            System.out.println( "**** " + wrapper.getData().getCardNumber());
            System.out.println( "**** " + wrapper.getData().getFirstName());
            System.out.println("************************************************");

            TransferWrapper.<DataTransferObject>builder()
                .data(card)
                .dataType(card != null ? card.getClass().getName() : null)
                .sourceProject(wrapper.getSourceProject())
                .destinationProject(wrapper.getDestinationProject())
                .correlationId(wrapper.getCorrelationId())
                .timestamp(wrapper.getTimestamp())
                .schemaVersion(wrapper.getSchemaVersion())
                .build();

            TransferTypeRegistry.register(Card.class.getName(), Card.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            kafkaProducerService.sendMessageInWrapper(wrapper);

//            ObjectMapper xmlMapper = new XmlMapper(); // from jackson-dataformat-xml
//            String xml = xmlMapper.writeValueAsString(wrapper);
//            TransferWrapper<?> fromXml = xmlMapper.readValue(xml, TransferWrapper.class);

        };
    }

    @Bean
    public CommandLineRunner wrapperUser(
        UserService userService,
        KafkaProducerService kafkaProducerService
    ) {
        return args -> {
            Faker faker = new Faker();
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);
            TransferWrapper<User> wrapper = new TransferWrapper<>(
                user,
                "user-service",
                "notification-service"
            );

            System.out.println("user : " + user.toString());
            System.out.println("************************************************");
            System.out.println( "**** " + wrapper);
            System.out.println( "**** " + wrapper.getData().getRole());
            System.out.println( "**** " + wrapper.getData().toString());
            System.out.println("************************************************");

            TransferWrapper.<DataTransferObject>builder()
                .data(user)
                .dataType(user != null ? user.getClass().getName() : null)
                .sourceProject(wrapper.getSourceProject())
                .destinationProject(wrapper.getDestinationProject())
                .correlationId(wrapper.getCorrelationId())
                .timestamp(wrapper.getTimestamp())
                .schemaVersion(wrapper.getSchemaVersion())
                .build();

            TransferTypeRegistry.register(User.class.getName(), User.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            kafkaProducerService.sendMessageInWrapper(wrapper);

//            ObjectMapper xmlMapper = new XmlMapper(); // from jackson-dataformat-xml
//            String xml = xmlMapper.writeValueAsString(wrapper);
//            TransferWrapper<?> fromXml = xmlMapper.readValue(xml, TransferWrapper.class);

        };
    }

    // @Bean
    public CommandLineRunner wrapperTransaction(
        UserService userService,
        KafkaProducerService kafkaProducerService
    ) {
        return args -> {
            Faker faker = new Faker();
            Transaction transaction = new Transaction();
            transaction.setId(UUID.fromString(new Faker().internet().uuid()));
            transaction.setAmount(BigDecimal.valueOf(new Faker().number().randomDouble(2, 10, 1000)));
            TransferWrapper<Transaction> wrapper = new TransferWrapper<>(
                transaction,
                "user-service",
                "notification-service"
            );

            System.out.println("user : " + transaction.toString());
            System.out.println("************************************************");
            System.out.println( "**** " + wrapper);
            System.out.println( "**** " + wrapper.getData().getAmount());
            System.out.println( "**** " + wrapper.getData().toString());
            System.out.println("************************************************");

            TransferWrapper.<DataTransferObject>builder()
                .data(transaction)
                .dataType(transaction != null ? transaction.getClass().getName() : null)
                .sourceProject(wrapper.getSourceProject())
                .destinationProject(wrapper.getDestinationProject())
                .correlationId(wrapper.getCorrelationId())
                .timestamp(wrapper.getTimestamp())
                .schemaVersion(wrapper.getSchemaVersion())
                .build();

            TransferTypeRegistry.register(Transaction.class.getName(), Transaction.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            kafkaProducerService.sendMessageInWrapper(wrapper);

//            ObjectMapper xmlMapper = new XmlMapper(); // from jackson-dataformat-xml
//            String xml = xmlMapper.writeValueAsString(wrapper);
//            TransferWrapper<?> fromXml = xmlMapper.readValue(xml, TransferWrapper.class);

        };
    }

    // @Bean
    public CommandLineRunner wrapperClass(
        UserService userService,
        KafkaTemplate<String, String> kafkaTemplate
    ) {
        return args -> {
            Card card = cardCreating();
            TransferWrapper<Card> wrapper = new TransferWrapper<>(
                card,
                "user-service",
                "notification-service"
            );
            TransferWrapperSerializer serializer = new TransferWrapperSerializer();
            String serializedWrapper = serializer.serialize(wrapper);

            kafkaTemplate.send(topic, serializedWrapper);

        };
    }

    private Card cardCreating() {
        Faker faker = new Faker();
        Card card = new Card();
        card.setCardNumber(faker.number().digits(10).toString());
        card.setFirstName(faker.name().firstName());
        card.setLastName(faker.name().lastName());
        card.setPin1(faker.number().digits(10).toString());
        card.setPin2(faker.number().digits(10).toString());
        return card;
    }




}