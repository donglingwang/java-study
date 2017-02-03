package dlwang.study.crypt.cipher;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

public class RSACoderTest {

	private byte [] publicKey;
	private byte [] privateKey;
	
	@Before
	public void initKey() throws Exception {
		Map<String, Object> keyMap = RSACoder.initKey();
		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
		
		System.out.println("公钥："+Base64.encodeBase64String(publicKey));
		System.out.println("私钥："+Base64.encodeBase64String(privateKey));
	}
	
	@Test
	public void test() throws Exception {
		System.out.println("私钥加密---公钥解密");
		String input1 = "RSA加密算法";
		byte [] data1 = RSACoder.encryptByPrivateKey(input1.getBytes(), privateKey);
		System.out.println("加密后的数据："+Base64.encodeBase64String(data1));
		
		byte [] output1 = RSACoder.decryptByPublicKey(data1, publicKey);
		System.out.println("解密："+new String(output1));
		
		System.out.println("------------------------------------------------");

		System.out.println("公钥加密---私钥解密");
		String input2 = "RSA";
		byte [] data2 = RSACoder.encryptByPublicKey(input2.getBytes(), publicKey);
		System.out.println("加密后的数据："+Base64.encodeBase64String(data2));
		
		byte [] output2 = RSACoder.decryptByPrivateKey(data2, privateKey);
		System.out.println("解密："+new String(output2));
	}
}
