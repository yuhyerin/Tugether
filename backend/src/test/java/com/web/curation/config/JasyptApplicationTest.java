package com.web.curation.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptApplicationTest {
	
    private final String email = "";
	
    private final String email_password = "";
	
    private final String db_username = "";
	
    private final String db_password = "";
	
	@Test
    void contextLoads() {
    }

    @Test
    void jasypt() {
    	System.out.println("=== email === ");
        System.out.println(jasyptEncoding(email));
        System.out.println("=== email_password === ");
        System.out.println(jasyptEncoding(email_password));
        System.out.println("=== db_username === ");
        System.out.println(jasyptEncoding(db_username));
        System.out.println("=== db_password === ");
        System.out.println(jasyptEncoding(db_password));
    }

    public String jasyptEncoding(String value) {

        String key = "my_jasypt_key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}
