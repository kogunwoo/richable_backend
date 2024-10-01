package com.idle.kb_i_dle_backend.domain.member.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecureKeyGenerator {
    public static String generateKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[keyLength];
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);

//        TPk93NCNEQKs66+Ht89m+qVM8WkXoysjxanI7qh9hK0=
    }

    public static void main(String[] args) {
        // 256비트 (32바이트) 키 생성
        String encryptionKey = generateKey(32);
        System.out.println("Generated Encryption Key: " + encryptionKey);
    }
}
