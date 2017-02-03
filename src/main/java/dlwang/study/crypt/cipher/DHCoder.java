package dlwang.study.crypt.cipher;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * ��Կ�����㷨
 * �ԳƼ�����ǶԳƼ��ܵ�һ�ֹ���
 * @author DongLing
 *
 */
public class DHCoder {

	private static final String KEY_ALGORITHM = "DH";
	private static final String CIPHER_ALGORITHM = "AES";
	
	/**
	 * DH�㷨��Կ����
	 */
	private static final int KEY_SIZE = 512;
	
	private static final String PUBLIC_KEY = "DHPublicKey";
	private static final String PRIVATE_KEY = "DHPrivateKey";
	
	/**
	 * ��ʼ���׷���Կ
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> initKey() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		kpg.initialize(KEY_SIZE);
		KeyPair keyPair = kpg.generateKeyPair();
		
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * ��ʼ���ҷ���Կ
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(byte [] key) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// ������Կ
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
		// �ɼ׷���Կ�����ҷ���Կ
		DHParameterSpec dhParameterSpec = ((DHPublicKey)pubKey).getParams();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// ��ʼ����Կ������
		keyPairGenerator.initialize(dhParameterSpec);
		
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * ����
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] encrypt(byte [] data,byte [] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, CIPHER_ALGORITHM);
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * ����
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] decrypt(byte [] data,byte [] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, CIPHER_ALGORITHM);
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * ���ɱ�����Կ
	 * @param pubKey
	 * @param priKey
	 * @return
	 * @throws Exception
	 */
	public static byte [] getSecretKey(byte [] pubKey,byte [] priKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
		keyAgree.init(privateKey);
		keyAgree.doPhase(publicKey, true);
		
		SecretKey secretKey = keyAgree.generateSecret(CIPHER_ALGORITHM);
		return secretKey.getEncoded();
	}
	
	/**
	 * ȡ��˽Կ
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte [] getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	
	/**
	 * ȡ�ù�Կ
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte [] getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key)keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
 }
