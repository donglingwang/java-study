package dlwang.study.crypt.aes;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AesTest {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	public static void main(String[] args) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);
		
		SecretKey key = kg.generateKey();
		
		Cipher cipher = Cipher.getInstance("AES", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		byte [] result = cipher.doFinal("王栋凌123，。。。，【平【饭店客房来看".getBytes());
		System.out.println(Base64.encodeBase64String(result));
		
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		byte [] output = cipher.doFinal(result);
		System.out.println(new String(output));
		
	}
}
