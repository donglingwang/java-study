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
	 * ����ͨ���ַ������м���
	 * @throws Exception
	 */
	public static void desCipher() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGenerator.generateKey();
		
		byte [] keyBytes = secretKey.getEncoded();
		Key key = new SecretKeySpec(keyBytes, "DES");
		
		Cipher cipher = Cipher.getInstance("DES");
		
		//TODO �԰�װ�ͽ�����Ǻܶ�
		//cipher.init(Cipher.WRAP_MODE, secretKey);
		//byte [] k = cipher.wrap(key);
		
		// ���ܲ���
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte [] result = cipher.doFinal("������,,,������+++".getBytes());
		
		// ���ܲ���
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte [] output = cipher.doFinal(result);
		
		System.out.println(new String(output));
	}
	
	/**
	 * ����������������м���
	 * @throws Exception
	 */
	public static void cipherInputStream() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGenerator.generateKey();
		
		Cipher cipher = Cipher.getInstance("DES");
		
		// ���ܽ����ܺ������д���ļ�
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		String input = "������123232��������****";
		CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(new File("src/main/java/secret.txt")), cipher);
		DataOutputStream dos = new DataOutputStream(cos);
		dos.writeUTF(input);
		dos.flush();
		dos.close();
		
		// ʹ�ü���ģʽ�����ļ��������ܺ������
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		CipherInputStream cis = new CipherInputStream(new FileInputStream(new File("src/main/java/secret.txt")), cipher);
		
		DataInputStream dis = new DataInputStream(cis);
		String output = dis.readUTF();
		System.out.println(output);
		dis.close();
	}
 	
	/**
	 * �������л��˵Ķ���
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
