package dlwang.study.crypt.common;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptoTest {

	// mac��ϢժҪ�㷨
	public static void mac() throws Exception {
		byte [] input = "MAC".getBytes();
		// 
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
		SecretKey secretKey = keyGenerator.generateKey();
		
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		
		byte [] result = mac.doFinal(input);
		
		System.out.println(Base64.encodeBase64String(result));
		
	}
	
	//TODO ���Ǻܶ�
	public static void keyAgreement() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DH");
		KeyPair keyPair1 = kpg.genKeyPair();
		KeyPair keyPair2 = kpg.genKeyPair();
		
		KeyAgreement keyAgreement = KeyAgreement.getInstance(kpg.getAlgorithm());
		// ��ʼ��
		keyAgreement.init(keyPair2.getPrivate());
		// ִ�мƻ�
		keyAgreement.doPhase(keyPair1.getPublic(), true);
		// ����SecretyKey
		SecretKey secretKey = keyAgreement.generateSecret("DES");
		
		System.out.println(Base64.encodeBase64String(secretKey.getEncoded()));
	}
	
	// ������Կ����
	public static void secretKeyFactory() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secretKey1 = keyGenerator.generateKey();
		byte [] key = secretKey1.getEncoded();
		
		// �����ֽ����ݻ�ԭ��Կ����
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey  secretKey2 = keyFactory.generateSecret(dks);
	}
	
	public static void main(String[] args) throws Exception {
		//mac();
		keyAgreement();
	}
}
