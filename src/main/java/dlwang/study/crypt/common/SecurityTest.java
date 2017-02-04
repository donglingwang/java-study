package dlwang.study.crypt.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.NEW;

import sun.security.util.DisabledAlgorithmConstraints;

public class SecurityTest {
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	
	public static String getMsgDigest() throws NoSuchAlgorithmException {
		byte [] input = "SHA".getBytes();
		MessageDigest digest = MessageDigest.getInstance("SHA");
		digest.update(input);
		byte [] result = digest.digest();
		
		return Base64.encodeBase64String(result);
	}
	
	
	public static String getMsgDigestUseInputStream() throws NoSuchAlgorithmException, IOException {
		byte [] input = "md5".getBytes();
		MessageDigest digest = MessageDigest.getInstance("MD5");
		DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(input), digest);
		
		dis.read(input, 0, input.length);
		
		byte [] result = dis.getMessageDigest().digest();
		
		dis.close();
		
		return Base64.encodeBase64String(result);
		
	}
	
	public static String getMsgDigestUseOutputStream() throws NoSuchAlgorithmException, IOException{
		byte [] input = "md5".getBytes();
		MessageDigest digest = MessageDigest.getInstance("MD5");
		DigestOutputStream dos = new DigestOutputStream(new ByteArrayOutputStream(), digest);
		
		dos.write(input, 0, input.length);
		
		byte [] result = dos.getMessageDigest().digest();
		dos.flush();
		dos.close();
		
		return Base64.encodeBase64String(result);
	}
	
	public static void algorithmParameters() throws NoSuchAlgorithmException, IOException {
		AlgorithmParameters ap = AlgorithmParameters.getInstance("DES");
		ap.init(new BigInteger("19050619766489163472469").toByteArray());
		
		byte [] b = ap.getEncoded();
		
		System.out.println(new BigInteger(b).toString());
	}
	
	public static void algorithmParameterGenerator() throws Exception {
		AlgorithmParameterGenerator apg = AlgorithmParameterGenerator.getInstance("DES");
		apg.init(56);
		AlgorithmParameters ap = apg.generateParameters();
		
		byte [] b = ap.getEncoded();
		
		System.out.println(new BigInteger(b).toString());
	}
	
	// 密钥生成对
	public static void keyPairGenerator() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		kpg.initialize(1024);
		KeyPair keys = kpg.generateKeyPair();
		byte [] privateKeys = keys.getPrivate().getEncoded();
		byte [] publicKeys = keys.getPublic().getEncoded();
		
		System.out.println(Base64.encodeBase64String(privateKeys));
		System.out.println(Base64.encodeBase64String(publicKeys));
		
	}
	
	//KeyPair
	
	public static void keyPair() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		KeyPair keyPair = kpg.generateKeyPair();
		
		byte [] privateKeys = keyPair.getPrivate().getEncoded();
		
		System.out.println(Base64.encodeBase64String(privateKeys));
		//由私钥密钥数组获得密钥规范
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeys);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		System.out.println(Base64.encodeBase64String(privateKey.getEncoded()));
	}
 	
	public static void viewAllProviders() {
		for (Provider provider : Security.getProviders()) {
			System.out.println(provider);
			for (Map.Entry<Object, Object> entry : provider.entrySet()) {
				System.out.println("\t"+entry.getKey());
			}
		}
	}
	public static void main(String[] args) throws Exception {
		//System.out.println(Security.getProperty("security.provider.1"));
		
		//MessageDigestSpi spi = MessageDigest.getInstance("");
		
		//System.out.println(getMsgDigest());
		//System.out.println(getMsgDigestUseInputStream());
		//System.out.println(getMsgDigestUseOutputStream());
		
		//algorithmParameters();
		//algorithmParameterGenerator();
		//keyPairGenerator();
		
		/*SecureRandom random = new SecureRandom();
		random.setSeed(System.currentTimeMillis());
		System.out.println(random.nextInt(10000));*/
		
		//keyPair();
		
		viewAllProviders();
	}
	
	
}
