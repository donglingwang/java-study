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
 * 密钥交换算法
 * 对称加密向非对称加密的一种过渡
 * @author DongLing
 *
 */
public class DHCoder {

	private static final String KEY_ALGORITHM = "DH";
	private static final String CIPHER_ALGORITHM = "AES";
	
	/**
	 * DH算法密钥长度
	 */
	private static final int KEY_SIZE = 512;
	
	private static final String PUBLIC_KEY = "DHPublicKey";
	private static final String PRIVATE_KEY = "DHPrivateKey";
	
	/**
	 * 初始化甲方密钥
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
	 * 初始化乙方密钥
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(byte [] key) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
		// 由甲方公钥构建乙方密钥
		DHParameterSpec dhParameterSpec = ((DHPublicKey)pubKey).getParams();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥生成器
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
	 * 加密
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
	 * 解密
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
	 * 生成本地密钥
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
	 * 取得私钥
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte [] getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	
	/**
	 * 取得公钥
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte [] getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key)keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
 }
