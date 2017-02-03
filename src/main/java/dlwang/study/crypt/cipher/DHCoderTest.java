package dlwang.study.crypt.cipher;

import static org.junit.Assert.*;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

public class DHCoderTest {

	/**
	 * 甲方
	 */
	private byte [] publicKey1;
	private byte [] privateKey1;
	private byte [] key1;
	
	/**
	 * 乙方
	 */
	private byte [] publicKey2;
	private byte [] privateKey2;
	private byte [] key2;
	
	@Before
	public void initKey() throws Exception {
		// 甲方
		Map<String, Object> keyMap1 = DHCoder.initKey();
		publicKey1 = DHCoder.getPublicKey(keyMap1);
		privateKey1 = DHCoder.getPrivateKey(keyMap1);
		
		System.out.println("甲方公钥："+Base64.encodeBase64String(publicKey1));
		System.out.println("甲方私钥："+Base64.encodeBase64String(privateKey1));
		
		// 乙方
		Map<String, Object> keyMap2 = DHCoder.initKey(publicKey1);
		publicKey2 = DHCoder.getPublicKey(keyMap2);
		privateKey2 = DHCoder.getPrivateKey(keyMap2);

		System.out.println("乙方公钥："+Base64.encodeBase64String(publicKey2));
		System.out.println("乙方私钥："+Base64.encodeBase64String(privateKey2));
		
		key1 = DHCoder.getSecretKey(publicKey2, privateKey1);
		System.out.println("甲方本地私钥："+Base64.encodeBase64String(key1));
		
		key2 = DHCoder.getSecretKey(publicKey1, privateKey2);
		System.out.println("乙方本地私钥："+Base64.encodeBase64String(key2));
		
		assertArrayEquals(key1, key2);
	}
	
	
	@Test
	public void test() throws Exception {
		System.out.println("---------------------------------------------------");
		String input1 = "交换加密算法";
		System.out.println("原文："+input1);
		System.out.println("使用甲方对本地密钥进行加密");
		byte [] code1 = DHCoder.encrypt(input1.getBytes(), key1);
		System.out.println("加密："+Base64.encodeBase64String(code1));
		System.out.println("使用乙方进行解密");
		byte [] decode1 = DHCoder.decrypt(code1, key2);
		System.out.println("解密后的数据为："+new String(decode1));
		System.out.println("---------------------------------------------------");
		String input2 = "DH";
		System.out.println("原文："+input2);
		System.out.println("使用乙方本地密钥进行加密");
		byte [] code2 = DHCoder.encrypt(input2.getBytes(), key2);
		System.out.println("加密："+Base64.encodeBase64String(code2));
		System.out.println("使用甲方进行解密");
		byte [] decode2 = DHCoder.decrypt(code2, key1);
		System.out.println("解密后的数据为："+new String(decode2));
	}
}
