package com.daveroberge.kic;

import java.security.SecureRandom;

public class PasswordGenerator {
  public static final int DEFAULT_LENGTH = 30;
  private static final int[] VALID_CHARS = new int[]{
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
      'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
      't', 'u', 'v', 'w', 'x', 'y', 'z',
      '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
      '-', '$', '_', '@', '!', '#'
  };

  private final SecureRandom random = new SecureRandom();

  public String generate(int length) {
    StringBuilder password = new StringBuilder();

    while (true) {
      int candidate = random.nextInt(122);

      for (int c : VALID_CHARS) {
        if (candidate == c) {
          password.append(Character.toChars(candidate));
          break;
        }
      }

      if (password.length() == length) {
        break;
      }
    }

    return password.toString();
  }

}
