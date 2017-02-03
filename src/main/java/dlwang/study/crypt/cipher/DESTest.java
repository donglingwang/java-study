package dlwang.study.crypt.cipher;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DESTest {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static final String KEY_ALGORITHM = "DES";
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
	public static void des() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM,"BC");
		kg.init(64);
		SecretKey key = kg.generateKey();
		// 生成字符数组的密钥方便存储
		byte [] b = key.getEncoded();
		
		// 读取密钥，重新构造私密密钥
		DESKeySpec spec = new DESKeySpec(b);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM,"BC");
		SecretKey secretKey = factory.generateSecret(spec);
		
		// 加密
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte [] data = cipher.doFinal("王栋凌".getBytes());
		
		// 解密
		Cipher cipher2 = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
		cipher2.init(Cipher.DECRYPT_MODE, secretKey);
		byte [] result = cipher2.doFinal(data);
		
		
		System.out.println(new String(result));
		
		
	}
	public static void main(String[] args) {
		try {
			des();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
