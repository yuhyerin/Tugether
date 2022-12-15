package com.web.curation.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.web.curation.common.ResponseCode;
import com.web.curation.exception.TugetherException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CipherUtil {

	public static final String KEY = "012345678910111213141516".substring(0,16);
	public static final String ALGO = "AES/ECB/PKCS5Padding";

	public static String encrypt(String plainText) {

		try {
			Cipher cipher = Cipher.getInstance(ALGO);
			SecretKey secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes());
			
			return Base64.getEncoder().encodeToString(encryptedTextBytes);
			
		} catch (NoSuchAlgorithmException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);
			
		} catch (NoSuchPaddingException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);
			
		} catch (InvalidKeyException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);
			
		} catch (IllegalBlockSizeException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);
			
		} catch (BadPaddingException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);
			
		}
	}

	public static String decrypt(String cipherText) {

		try {

			Cipher cipher = Cipher.getInstance(ALGO);
			SecretKey secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedTextBytes = Base64.getDecoder().decode(cipherText);
			byte[] decrypted = cipher.doFinal(decryptedTextBytes);
			return new String(decrypted, "UTF-8");

		} catch (NoSuchAlgorithmException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);
			
		} catch (NoSuchPaddingException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);

		} catch (InvalidKeyException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);

		} catch (IllegalBlockSizeException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);

		} catch (BadPaddingException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);

		} catch (UnsupportedEncodingException e) {
			throw new TugetherException(ResponseCode.CIPHER_UTIL_ERROR);
		}

	}

}
