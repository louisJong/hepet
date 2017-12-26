package com.project.hepet.utils;

import java.nio.CharBuffer;
import java.util.Random;

public class MyStringUtil {
	
	public static void main(String[] args) {
//		System.out.println(getRandomPwd(12));
		System.out.println(getRandomWords(16));
//		System.out.println(getRandomCDMANum());
//		System.out.println(TripleDES.base64Encode(StringUtils.reverse("7aJQr4Y471").getBytes()));
		
	}

	public static String getRandomPwd(int length) {
		CharBuffer charBuff = CharBuffer.allocate(length);
		Random random = new Random();
		// 至少有1个特殊字符，随机数决定特殊字符的位置和值
		char specialChars[] = new char[]{'!','@','#','$','%','^','&'};
		int specialPos = random.nextInt(length-1);
		// 至少有1个大写字母
		int upperCasePos = (specialPos + 1) % length;
		// 至少有1个小写字母
		int lowerCasePos = (upperCasePos +1) % length;
		// 至少有1个数字
		int numPos = (lowerCasePos + 1) % length;
		for (int i = 0; i < length; i++) {
			if(i == specialPos) {
				char specialChar = specialChars[random.nextInt(6)];
				charBuff.append(specialChar);
			} else if(i == upperCasePos) {
				charBuff.append((char) (65 + random.nextInt(26)));
			} else if(i == lowerCasePos) {
				charBuff.append((char) (97 + random.nextInt(26)));
			} else if(i == numPos) {
				charBuff.append((char) (48 + random.nextInt(10)));
			} else {
				// 1/7概率为特殊字符
				int charType = random.nextInt(6);
				if(charType == 0) {
					charBuff.append(specialChars[random.nextInt(6)]);
				} else if(charType < 4) {
					charBuff.append((char) (48 + random.nextInt(10)));
				} else {
					boolean caseFlag = random.nextBoolean();
					int choice = caseFlag ? 65 : 97;
					charBuff.append((char) (choice + random.nextInt(26)));
				}
			}
		}
		charBuff.flip();
		return charBuff.toString();
	}

	public static String getRandomID() {
		return String.valueOf(new Random().nextLong());
	}
	
	public static String getRandomNums(int length) {
		CharBuffer charBuff = CharBuffer.allocate(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			charBuff.append((char) (48 + random.nextInt(10)));
		}
		charBuff.flip();
		return charBuff.toString();
	}

	public static String getRandomWords(int length) {
		CharBuffer charBuff = CharBuffer.allocate(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean charFlag = random.nextBoolean();
			if (charFlag)
			{
				boolean caseFlag = random.nextBoolean();
				int choice = caseFlag ? 65 : 97;
				charBuff.append((char) (choice + random.nextInt(26)));
			} else {
				charBuff.append((char) (48 + random.nextInt(10)));
			}
		}
		charBuff.flip();
		return charBuff.toString();
	}

	public static boolean isMobile(String str) {
		return str != null && str.matches("1\\d{10}");
	}

	public static boolean isCDMA(String str) {
		return str != null && str.matches("1(([35]3)|(8[019]))\\d{8}");
	}

	public static String getRandomCDMANum() {
		int hcodeId = (int)(Math.random()*7);
		String hcode[] = new String[]{"133","153","180","181","189","177", "1700"};
		String number = hcode[hcodeId];
		return number+getRandomNums(11-number.length());
	}
}
