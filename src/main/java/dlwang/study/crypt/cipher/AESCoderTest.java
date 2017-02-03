package dlwang.study.crypt.cipher;

import org.apache.commons.codec.binary.Base64;

public class AESCoderTest {

	public static void main(String[] args) throws Exception {
		String input = "AES";
		System.out.println("ԭ�ģ�"+input);
		
		byte [] key = AESCoder.initKey();
		System.out.println("��Կ��"+Base64.encodeBase64String(key));
		
		byte [] data = AESCoder.encrypt(input.getBytes(), key);
		System.out.println("���ܺ�"+Base64.encodeBase64String(data));
		
		byte [] result = AESCoder.decrypt(data, key);
		
		System.out.println("���ܣ�"+new String(result));
	}
}
