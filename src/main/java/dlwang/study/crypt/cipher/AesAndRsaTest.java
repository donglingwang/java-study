package dlwang.study.crypt.cipher;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.UrlBase64;
import org.junit.Before;
import org.junit.Test;

public class AesAndRsaTest {

	private byte [] publicKey;
	private byte [] privateKey;
	
	private byte [] key;
	
	@Before
	public void initKey() throws Exception {
		Map<String, Object> rsaKeyMap = RSACoder.initKey();
		publicKey = RSACoder.getPublicKey(rsaKeyMap);
		privateKey = RSACoder.getPrivateKey(rsaKeyMap);
	}
	

	@Test
	public void test() throws Exception {
		String input = "rsa+aes加密";
		// 1、初始化aes密钥
		key = AESCoder.initKey();
		System.out.println(key.length);
		System.out.println("客户端AES密钥:"+new String(UrlBase64.encode(key)));
		// 2、使用aes进行加密
		byte [] data = AESCoder.encrypt(input.getBytes(), key);
		// 3、使用公钥加密aes密钥
		byte [] rsaData = RSACoder.encryptByPublicKey(key, publicKey);
		
		// 4、使用私钥加密aes密钥
		byte [] serverAesKey = RSACoder.decryptByPrivateKey(rsaData, privateKey);
		System.out.println("解密后得到的aes密钥："+new String(UrlBase64.encode(serverAesKey)));
		// 5、使用解密后aes密钥对数据进行解密
		byte [] result = AESCoder.decrypt(data, serverAesKey);
		System.out.println("服务器端解密的结果为："+new String(result));
	}
	
}
