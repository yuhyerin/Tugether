package com.web.curation.util;

import java.util.Random;
import java.util.StringTokenizer;

public class RandomNumberUtil {
	
	public static String createRandomNumber() {
		StringBuilder key = null;
		Random rd = new Random();
		while (true) {
			key = new StringBuilder();
			for (int i = 0; i < 8; i++) {
				char temp = (char) (rd.nextInt(90) + 33);
				key.append(temp);
			}
			System.out.println(key.toString());
			StringTokenizer st = new StringTokenizer(key.toString(), "1234567890");
			if (st.countTokens() > 1) {
				System.out.println(st.toString() + ", " + st.countTokens());
				break;
			}
		}
		return key.toString(); // 8자리 랜덤한 숫자 생성
	}

}
