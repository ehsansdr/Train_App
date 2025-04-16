package com.example.trainproject.base.Util;

import com.example.trainproject.base.Constant.OrderStatus;
import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Model.Order;
import com.example.trainproject.base.Model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.javafaker.Faker;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ObjectIntrospector {

  public static void main(String[] args) {
    Faker faker = new Faker();
    Date pastDate = faker.date().past(10, java.util.concurrent.TimeUnit.DAYS);
    Instant instant = pastDate.toInstant();
    ZoneId zoneId = ZoneId.of("America/New_York");
    double randomDouble = faker.number().randomDouble(2, 1, 1000); // Example with 2 decimal places, between 1 and 1000

    // Convert the double to BigDecimal
    BigDecimal bigDecimalFromDouble = BigDecimal.valueOf(randomDouble);

    Card obj = new Card();
    obj.setCardNumber(faker.number().digits(10).toString());
    obj.setFirstName(faker.name().firstName());
    obj.setLastName(faker.name().lastName());
    obj.setPin1(faker.number().digits(10).toString());
    obj.setPin2(faker.number().digits(10).toString());
    IntrospectorMonitorTotal(obj);
    System.out.println("**************************");
    Order obj1 = new Order();
    obj1.setShortNumber(faker.number().numberBetween(1,10));
    obj1.setTotalAmount(faker.number().randomDouble(4, -50, 50));
    obj1.setOrderStatus(OrderStatus.PENDING);
    obj1.setDate(ZonedDateTime.ofInstant(instant, zoneId));
    IntrospectorMonitorTotal(obj1);
    System.out.println("**************************");
    Transaction obj2 = new Transaction();
    obj2.setAmount(bigDecimalFromDouble);
    IntrospectorMonitorTotal(obj2);
  }

  public  static void IntrospectorMonitorTotal(Object obj) {
    IntrospectorMonitor4(obj);
  }

  public static void IntrospectorMonitor(Object obj) {
    ObjectMapper objectMapper = new ObjectMapper();
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    JavaType javaType = typeFactory.constructType(obj.getClass());

    // Get the BeanDescription which provides information about the class
    List<BeanPropertyDefinition> properties = objectMapper.getSerializationConfig()
        .introspect(javaType)
        .findProperties();

    System.out.println("Fields of " + obj.getClass().getSimpleName() + ":");
    for (BeanPropertyDefinition property : properties) {
      String fieldName = property.getName();
      JavaType fieldType = property.getPrimaryType();
      System.out.println(
          "  Name: " + fieldName + ", Type: " + fieldType.getRawClass().getSimpleName());
    }
  }

  public static void IntrospectorMonitor2(Object obj) {
    ObjectMapper objectMapper = new ObjectMapper();
    TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
    Map<String, Object> dataMap;
    try {
      dataMap = objectMapper.readValue(obj.toString(), typeReference);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    JavaType javaType = typeFactory.constructType(obj.getClass());

    // Get the BeanDescription which provides information about the class
    List<BeanPropertyDefinition> properties = objectMapper.getSerializationConfig()
        .introspect(javaType)
        .findProperties();

    System.out.println("Fields of " + obj.getClass().getSimpleName() + ":");
    for (BeanPropertyDefinition property : properties) {
      String fieldName = property.getName();
      JavaType fieldType = property.getPrimaryType();
      System.out.println("  Name: " + fieldName + ", Type: " + fieldType.getRawClass().getSimpleName());
      //System.out.println("value : " + dataMap.get(fieldName));
    }
  }


  public static void IntrospectorMonitor3(Object obj) {
    ObjectMapper objectMapper = new ObjectMapper();
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    JavaType javaType = typeFactory.constructType(obj.getClass());

    // Get the BeanDescription which provides information about the class
    List<BeanPropertyDefinition> properties = objectMapper.getSerializationConfig()
        .introspect(javaType)
        .findProperties();

    System.out.println("Fields of " + obj.getClass().getSimpleName() + ":");
    for (BeanPropertyDefinition property : properties) {
      String fieldName = property.getName();
      JavaType fieldType = property.getPrimaryType();
      System.out.println("  Name: " + fieldName + ", Type: " + fieldType.getRawClass().getSimpleName());
//      System.out.println("value : " + properties.g);
      //System.out.println("value : " + dataMap.get(fieldName));
    }
  }

  public static void IntrospectorMonitor4(Object obj) {
    ObjectMapper objectMapper = new ObjectMapper();
    TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
    Map<String, Object> dataMap = null;
    try {
      // While this part caused an error before, it might be useful if 'obj.toString()'
      // actually returns a valid JSON string in some cases. If not, you can remove it.
      try {
        dataMap = objectMapper.readValue(obj.toString(), typeReference);
      } catch (JsonProcessingException e) {
        // If parsing as JSON fails, we'll proceed with direct field access
        System.err.println("Warning: Could not parse object string as JSON: " + e.getMessage());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    JavaType javaType = typeFactory.constructType(obj.getClass());

    // Get the BeanDescription which provides information about the class
    List<BeanPropertyDefinition> properties = objectMapper.getSerializationConfig()
        .introspect(javaType)
        .findProperties();

    System.out.println("Fields of " + obj.getClass().getSimpleName() + ":");
    for (BeanPropertyDefinition property : properties) {
      String fieldName = property.getName();
      JavaType fieldType = property.getPrimaryType();
      System.out.print("  Name: " + fieldName + ", Type: " + fieldType.getRawClass().getSimpleName() + " , ");

      try {
        // Use reflection to get the actual field object
        Field field = obj.getClass().getDeclaredField(fieldName);
        // Make private fields accessible
        field.setAccessible(true);
        // Get the value of the field
        Object value = field.get(obj);
        System.out.println("Value: " + value);
      } catch (NoSuchFieldException e) {
        System.err.println("    Error: Field '" + fieldName + "' not found using reflection.");
      } catch (IllegalAccessException e) {
        System.err.println("    Error: Could not access field '" + fieldName + "'.");
      }
      // If dataMap was successfully populated from JSON, you could also try to get the value from there
      // if (dataMap != null && dataMap.containsKey(fieldName)) {
      //     System.out.println("    Value from dataMap: " + dataMap.get(fieldName));
      // }
    }
  }


}
