package dlwang.study.crypt.aes;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class JDKAes {

	public static void main(String[] args) {
		jdkAES();
		bcAES();
	}
	
	public static void jdkAES() {
		
		try {
			// 生成KEY
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256);
			SecretKey secretKey = keyGenerator.generateKey();
			byte [] keyBytes = secretKey.getEncoded();
			
			// KEY转换
			Key key = new SecretKeySpec(keyBytes, "AES");
			
			// 加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte [] result = cipher.doFinal("wangdongling".getBytes());
			System.out.println("jdk aes encrypt ："+ Base64.encodeBase64String(result));
			
			// 解密
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(result);
			
			System.out.println("origin string: "+ new String(result));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void bcAES() {
		
		try {
			Security.addProvider(new BouncyCastleProvider());
			
			
			// 生成KEY
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES","BC");
			keyGenerator.init(256);
			SecretKey secretKey = keyGenerator.generateKey();
			byte [] keyBytes = secretKey.getEncoded();
			
			// KEY转换
			Key key = new SecretKeySpec(keyBytes, "AES");
			
			// 加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding","BC");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte [] result = cipher.doFinal("wangdongling".getBytes());
			System.out.println("jdk aes encrypt ："+ Base64.encodeBase64String(result));
			
			// 解密
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(result);
			
			System.out.println("origin string: "+ new String(result));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
