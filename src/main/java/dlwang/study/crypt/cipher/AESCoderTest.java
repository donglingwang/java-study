package dlwang.study.crypt.cipher;

import org.apache.commons.codec.binary.Base64;

public class AESCoderTest {

	public static void main(String[] args) throws Exception {
		String input = "AES";
		System.out.println("原文："+input);
		
		byte [] key = AESCoder.initKey();
		System.out.println("密钥："+Base64.encodeBase64String(key));
		
		byte [] data = AESCoder.encrypt(input.getBytes(), key);
		System.out.println("加密后："+Base64.encodeBase64String(data));
		
		byte [] result = AESCoder.decrypt(data, key);
		
		System.out.println("解密："+new String(result));
	}
}
