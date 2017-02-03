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
		String input = "rsa+aes����";
		// 1����ʼ��aes��Կ
		key = AESCoder.initKey();
		System.out.println(key.length);
		System.out.println("�ͻ���AES��Կ:"+new String(UrlBase64.encode(key)));
		// 2��ʹ��aes���м���
		byte [] data = AESCoder.encrypt(input.getBytes(), key);
		// 3��ʹ�ù�Կ����aes��Կ
		byte [] rsaData = RSACoder.encryptByPublicKey(key, publicKey);
		
		// 4��ʹ��˽Կ����aes��Կ
		byte [] serverAesKey = RSACoder.decryptByPrivateKey(rsaData, privateKey);
		System.out.println("���ܺ�õ���aes��Կ��"+new String(UrlBase64.encode(serverAesKey)));
		// 5��ʹ�ý��ܺ�aes��Կ�����ݽ��н���
		byte [] result = AESCoder.decrypt(data, serverAesKey);
		System.out.println("�������˽��ܵĽ��Ϊ��"+new String(result));
	}
	
}
