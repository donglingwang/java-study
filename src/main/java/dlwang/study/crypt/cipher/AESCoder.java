package dlwang.study.crypt.cipher;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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
}
