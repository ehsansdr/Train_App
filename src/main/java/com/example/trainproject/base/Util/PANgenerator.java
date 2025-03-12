package com.example.trainproject.base.Util;

import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

public class PANgenerator {

  // Generate a random 16-digit PAN adhering to ISO 8583 standard
  public static String generatePAN(BigDecimal lastSequence)
      throws Exception {
    // The first 6 digits are typically the issuer identification number (IIN)
    String bin = "669012"; // Example IIN, replace with actual IIN range
    StringBuilder sb = new StringBuilder(bin);

    // Modified service Code based on financial service
    sb.append("00");
    sb.append(StringUtils.leftPad(lastSequence.toString(), 7, "0"));
    String partialPan = sb.toString();
    return partialPan + generateLastNumber(partialPan);
  }

  private static char generateLastNumber(String cardNumber) throws Exception {
    if (cardNumber.length() != 15) {
      throw new Exception("cardNumber Length is not valid !!!");
    }
    int first = 2;
    int second = 1;
    int constantNum = 9;
    boolean isFirstNumber = true;
    int sum = 0;
    for (char eachDigit : cardNumber.toCharArray()) {
      int result = Math.multiplyExact(Integer.parseInt(String.valueOf(eachDigit)),
          ((isFirstNumber) ? first : second));
      sum += ((result) > constantNum) ? Math.subtractExact(result, constantNum) : result;
      isFirstNumber = !isFirstNumber;
    }
    String stringResult = String.valueOf(Math.multiplyExact(sum, 9));
    return stringResult.charAt(stringResult.length() - 1);
  }

}
