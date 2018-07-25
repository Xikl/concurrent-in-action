package com.ximo.thread.design.pattern.chap10.thread.specific;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author 朱文赵
 * @date 2018/7/16 12:09
 * @description
 */
public class ThreadSpecificSecureRandom {

    public static final ThreadSpecificSecureRandom INSTANCE = new ThreadSpecificSecureRandom();

    private static final ThreadLocal<SecureRandom> SECURE_RANDOM = ThreadLocal.withInitial(() -> {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    });

    private ThreadSpecificSecureRandom() {
    }

    public int nextInt(int upperBound) {
        SecureRandom secureRandom = SECURE_RANDOM.get();
        return secureRandom.nextInt(upperBound);
    }

    public void setSeed(long seed) {
        SecureRandom secureRandom = SECURE_RANDOM.get();
        secureRandom.setSeed(seed);
    }




}
