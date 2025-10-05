package com.example.trainproject;

import com.example.trainproject.base.Config.MessageConfig;
import com.example.trainproject.base.Constant.CardStatus;
import com.example.trainproject.base.Constant.Role;
import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Model.KafkaProduceMessage;
import com.example.trainproject.base.Model.Transaction;
import com.example.trainproject.base.Model.User;
import com.example.trainproject.base.Repository.CardRepository;
import com.example.trainproject.base.Repository.TransactionRepository;
import com.example.trainproject.base.Repository.UserRepository;
import com.example.trainproject.base.Service.NotificationService;
import com.example.trainproject.base.Service.handler.NotificationProxy;
import com.example.trainproject.base.Util.Wapper.DataTransferObject;
import com.example.trainproject.base.Util.Wapper.KafkaTransferWrapperSerializer;
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
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
@EnableCaching
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
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TrainProjectApplication.class, args);
        NotificationProxy proxy = new NotificationProxy();
        proxy.setNotificationHandler(new NotificationService());
        proxy.handle("hi from proxy");
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

    // @Bean
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
        card.setPin1(faker.number().digits(10).toString());
        card.setPin2(faker.number().digits(10).toString());
        return card;
    }

    public CommandLineRunner wrapper(
        KafkaTransferWrapperSerializer kafkaTransferWrapperSerializer
    ){
        return args -> {
            Faker faker = new Faker();
            Card card = new Card();
            card.setCardNumber(faker.number().digits(10).toString());
            card.setPin1(faker.number().digits(10).toString());
            card.setPin2(faker.number().digits(10).toString());

            TransferWrapper<Card> cardWrappered = new TransferWrapper<>();
            kafkaTransferWrapperSerializer.serialize(topic, cardWrappered);

        };
    }


    // @Bean
    public CommandLineRunner transactionCreation(
        TransactionRepository transactionRepository
    ){
        return args -> {
            for (int i = 0;i < 11;i++){
            Faker faker = new Faker();
            Transaction transaction = new Transaction();
            transaction.setAmount(BigDecimal.valueOf(new Faker().number().randomDouble(2, 10, 1000)));
            transactionRepository.save(transaction);
            }
        };
    }

    // @Bean
    public CommandLineRunner cardCreation(
        CardRepository cardRepository,
        UserRepository userRepository
    ){
        return args -> {
            User user = new User();
            Optional<User> userOptional =
                userRepository.findById(UUID.fromString("9de28244-f164-48fc-92b0-e8c305c0704e")).or(() -> userRepository.findByUsername(faker.name().firstName()));
            for (int i = 0;i < 11;i++){
                Faker faker = new Faker();

                Card card = new Card();
                card.setCardNumber(faker.number().digits(10).toString());
                card.setPin1(faker.number().digits(4).toString());
                card.setPin2(faker.number().digits(6).toString());
                card.setUser(userOptional.orElse(new User()));
                card.setCardNumber(UUID.randomUUID().toString());
                card.setStatus(CardStatus.ENABLE);
                cardRepository.save(card);
            }
        };
    }



}