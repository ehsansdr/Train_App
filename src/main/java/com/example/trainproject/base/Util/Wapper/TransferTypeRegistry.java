package com.example.trainproject.base.Util.Wapper;

import java.util.HashMap;
import java.util.Map;

public class TransferTypeRegistry {
  // Safe Deserialization with a Registry
  private static final Map<String, Class<? extends DataTransferObject>> registry = new HashMap<>();

  public static void register(String typeName, Class<? extends DataTransferObject> clazz) {
    registry.put(typeName, clazz);
  }

  public static Class<? extends DataTransferObject> getClassForType(String typeName) {
    return registry.get(typeName);
  }
}

