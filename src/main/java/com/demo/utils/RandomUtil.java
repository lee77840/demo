package com.demo.utils;

import java.security.SecureRandom;

public class RandomUtil {

	private RandomUtil() {
		// Do nothing
	}

	public static String randomAlphanumeric(int length) {
		SecureRandom secureRandom = new SecureRandom();
		String charactorSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		StringBuilder buff = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int offset = secureRandom.nextInt(charactorSet.length());
			buff.append(charactorSet.substring(offset, offset + 1));
		}

		return buff.toString();
	}

}
