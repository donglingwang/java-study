package dlwang.study.crypt.cipher;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESCoder {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static final String KEY_ALGORITHM = "AES";
	public static final String CIPER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	public static final String CIPER_ALGORITHM_BC = "AES/ECB/PKCS7Padding";
	
	private static Key toKey(byte [] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		return secretKey;
	}
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] decrypt(byte [] data,byte [] key) throws Exception {
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPER_ALGORITHM_BC,"BC");
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] encrypt(byte [] data,byte [] key) throws Exception {
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPER_ALGORITHM_BC,"BC");
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	public static byte [] getKey(String aesKey) {
		return Base64.decodeBase64(aesKey);
	}
	
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] decrypt(byte [] data,String key) throws Exception {
		return decrypt(data, getKey(key));
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte [] encrypt(byte [] data,String key) throws Exception {
		return encrypt(data, getKey(key));
	}
	
	/**
	 * 初始化Key
	 * @return
	 * @throws Exception
	 */
	public static byte [] initKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM,"BC");
		kg.init(256);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	
	public static String initKeyString () throws Exception {
		byte [] key = initKey();
		return Base64.encodeBase64String(key);
	}
}
