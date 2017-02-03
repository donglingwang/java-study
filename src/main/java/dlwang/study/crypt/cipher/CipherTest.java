package dlwang.study.crypt.cipher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherTest {

	/**
	 * 对普通的字符串进行加密
	 * @throws Exception
	 */
	public static void desCipher() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGenerator.generateKey();
		
		byte [] keyBytes = secretKey.getEncoded();
		Key key = new SecretKeySpec(keyBytes, "DES");
		
		Cipher cipher = Cipher.getInstance("DES");
		
		//TODO 对包装和解包不是很懂
		//cipher.init(Cipher.WRAP_MODE, secretKey);
		//byte [] k = cipher.wrap(key);
		
		// 加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte [] result = cipher.doFinal("王栋凌,,,，，，+++".getBytes());
		
		// 解密操作
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte [] output = cipher.doFinal(result);
		
		System.out.println(new String(output));
	}
	
	/**
	 * 根据输入输出流进行加密
	 * @throws Exception
	 */
	public static void cipherInputStream() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGenerator.generateKey();
		
		Cipher cipher = Cipher.getInstance("DES");
		
		// 加密将加密后的数据写入文件
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		String input = "王栋凌123232。。。。****";
		CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(new File("src/main/java/secret.txt")), cipher);
		DataOutputStream dos = new DataOutputStream(cos);
		dos.writeUTF(input);
		dos.flush();
		dos.close();
		
		// 使用加密模式，从文件读出解密后的数据
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		CipherInputStream cis = new CipherInputStream(new FileInputStream(new File("src/main/java/secret.txt")), cipher);
		
		DataInputStream dis = new DataInputStream(cis);
		String output = dis.readUTF();
		System.out.println(output);
		dis.close();
	}
 	
	/**
	 * 加密序列化了的对象
	 * @throws Exception
	 */
	public static void sealedObject() throws Exception {
		String input = "SealedObject";
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey key = keyGenerator.generateKey();
		Cipher cipher1 = Cipher.getInstance(key.getAlgorithm());
		
		cipher1.init(Cipher.ENCRYPT_MODE, key);
		
		SealedObject sealedObject = new SealedObject(input, cipher1);
		Cipher cipher2 = Cipher.getInstance(key.getAlgorithm());
		cipher2.init(Cipher.DECRYPT_MODE, key);
		
		String output = (String)sealedObject.getObject(cipher2);
		
		System.out.println(output);
	}
	public static void main(String[] args) throws Exception {
		//desCipher();
		//cipherInputStream();
		sealedObject();
	}
}
