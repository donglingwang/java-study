package dlwang.study.crypt.cipher;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * RSA�ǶԳƼ����㷨
 * @author DongLing
 *
 */
public class RSACoder {

	private static final String KEY_ALGORITHM = "RSA";
	
	private static final String PUBLIC_KEY = "RSAPulbicKey";
	
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	/**
	 * Ĭ��1024
	 * ��Կ������512~65536֮��
	 * ������64�ı���
	 */
	private static final int KEY_SIZE = 512;
	
	/**
	 * ʹ��˽Կ���н���
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] decryptByPrivateKey(byte [] data, byte [] key) throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * ʹ�ù�Կ���н���
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] decryptByPublicKey(byte [] data, byte [] key) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * ʹ��˽Կ���м���
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] encryptByPrivateKey(byte [] data, byte [] key) throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * ʹ�ù�Կ���м���
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] encryptByPublicKey(byte [] data, byte [] key) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * ʹ��˽Կ���н���
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] decryptByPrivateKey(byte [] data, String key) throws Exception {
		return decryptByPrivateKey(data, toKey(key));
	}
	
	/**
	 * ʹ�ù�Կ���н���
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] decryptByPublicKey(byte [] data, String key) throws Exception {
		return decryptByPublicKey(data, toKey(key));
	}
	
	/**
	 * ʹ��˽Կ���м���
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] encryptByPrivateKey(byte [] data, String key) throws Exception {
		return encryptByPrivateKey(data, toKey(key));
	}
	
	/**
	 * ʹ�ù�Կ���м���
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] encryptByPublicKey(byte [] data, String key) throws Exception {
		return encryptByPublicKey(data, toKey(key));
	}
	
	public static byte [] getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	 
	public static byte [] getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	
	/**
	 * ��ʼ����Կ
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		kpg.initialize(KEY_SIZE);
		KeyPair keyPair = kpg.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		keyMap.put(PRIVATE_KEY, privateKey);
		keyMap.put(PUBLIC_KEY, publicKey);
		return keyMap;
	}
	
	
	public static byte [] toKey(String rsaKey) throws Exception {
		return Base64.decodeBase64(rsaKey);
	}
}
