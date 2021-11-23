/***********************************************************************************
 * FILE: PasswordEncryptionArgon2.java
 * DESC :
 *   1. Customer password encryption
 * PROJ : Spring Boot Framework. 
 * -------------------------------------------------------------------------------------
 *                  Modification History
 * -------------------------------------------------------------------------------------
 *  DATE           AUTHOR              FUNCTION        DESCRIPTION
 * -------------  -----------------	--------------  --------------------
 * 2021/10/15     Sangyoub Lee                        Customer password encryption 
 * -------------  -----------------	--------------  --------------------
 ***********************************************************************************/
package com.demo.utils;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;

@Component
public class PasswordEncryptionArgon2 {
	
	
	private static int saltLength;
	@Value("${argon2.saltLength}")
	private void setSaltLength(int temp) {
		saltLength = temp;
	}
	
	private static int outputLength;
	@Value("${argon2.outputLength}")
	private void setOutputLength(int temp) {
		outputLength = temp;
	}
	
	private static int opsLimit;
	@Value("${argon2.opsLimit}")
	private void setOpsLimit(int temp) {
		opsLimit = temp;
	}
	
	private static int version;
	@Value("${argon2.version}")
	private void setVersion(int temp) {
		saltLength = temp;
	}
	
	private static int parallelism;
	@Value("${argon2.parallelism}")
	private void setParallelism(int temp) {
		parallelism = temp;
	}
	
	private static int memLimit;
	@Value("${argon2.memLimit}")
	private void setMemLimit(int temp) {
		memLimit = temp;
	}
	//compile group: 'org.apache.commons', name: 'commons-lang3', version: '3.9'
    public static byte[] generateArgon2idToByte(String password) {
    	
    	String salt = generateSalt16String(saltLength);

    	Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) // 19
                .withIterations(opsLimit)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt.getBytes());
        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());
        byte[] result = new byte[outputLength];
        gen.generateBytes(password.getBytes(), result, 0, result.length);

        return result;
    }
    
    public static byte[] generateArgon2idToByte(String password, String salt) {

    	Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) // 19
                .withIterations(opsLimit)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt.getBytes(StandardCharsets.UTF_8));
        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());
        byte[] result = new byte[outputLength];
        gen.generateBytes(password.getBytes(), result, 0, result.length);

        return result;
    }
    
    public static String generateArgon2idToBase64(String password) {
    	
    	String salt = generateSalt16String(saltLength);
    	System.out.println(salt);
    	Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) // 19
                .withIterations(opsLimit)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt.getBytes());
        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());
        byte[] result = new byte[outputLength];
        gen.generateBytes(password.getBytes(), result, 0, result.length);

        return base64Encoding(result) + ":" + salt;
    }
    
    public static String generateArgon2idToBase64(String password, String salt) {

    	Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) // 19
                .withIterations(opsLimit)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt.getBytes(StandardCharsets.UTF_8));
        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());
        byte[] result = new byte[outputLength];
        gen.generateBytes(password.getBytes(), result, 0, result.length);

        return base64Encoding(result);
    }
    
    public static String generateArgon2idToHex(String password) {

    	String salt = generateSalt16String(saltLength);
    	
    	Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) // 19
                .withIterations(opsLimit)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt.getBytes());
        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());
        byte[] result = new byte[outputLength];
        gen.generateBytes(password.getBytes(), result, 0, result.length);

        return toHex(result) + ":" + salt;
    }
    
    public static String generateArgon2idToHex(String password, String salt) {

    	Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) // 19
                .withIterations(opsLimit)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt.getBytes(StandardCharsets.UTF_8));
        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());
        byte[] result = new byte[outputLength];
        gen.generateBytes(password.getBytes(), result, 0, result.length);

        return toHex(result);
    }

    private static byte[] generateSalt16Byte(String saltStr, int length) {
    	SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[length];
        secureRandom.nextBytes(salt);
        return salt;
    }
    
    private static String generateSalt16String(int length) {
        SecureRandom secureRandom = new SecureRandom();
        String charactorSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        StringBuffer buff = new StringBuffer(length);
        for(int i=0;i<length;i++) {
            int offset = secureRandom.nextInt(charactorSet.length());
            buff.append(charactorSet.substring(offset,offset+1));
        }
        
        return buff.toString();
    }
    
    public static String getSalt(byte[] salt) {

        StringBuffer sb = new StringBuffer();

        for(int i=0; i<salt.length; i++) {
        	sb.append(String.format("%02x", salt[i]));
        }     

        return sb.toString();
     }

    private static String base64Encoding(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }
    
    private static String toHex(byte[] hash) {
		StringBuilder stringBuilder = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			stringBuilder.append(String.format(Locale.US, "%02x", b));
		}
		return stringBuilder.toString();
    }
    
    private static String toHex1(byte[] hash) {
		StringBuilder stringBuilder = new StringBuilder(hash.length);
		for (byte b : hash) {
			stringBuilder.append(String.format(Locale.US, "%02x", b));
		}
		return stringBuilder.toString();
    }
}
