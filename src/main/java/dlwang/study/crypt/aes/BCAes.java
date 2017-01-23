package dlwang.study.crypt.aes;

import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BCAes {

	public static final String ALGORITHM = "AES/ECB/PKCS7Padding";  
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		// ����KEY
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(116);
		SecretKey secretKey = keyGenerator.generateKey();
		byte [] keyBytes = secretKey.getEncoded();
		
		//byte [] mykey = "12345678123456781234567812345678".getBytes();
		byte [] result = Aes256Encode("wangdongling", keyBytes);
		System.out.println(Base64.encodeBase64String(result));
		System.out.println(Aes256Decode(result, keyBytes));
		
	}
	
	
    
    /** 
     * @param  String str  Ҫ�����ܵ��ַ��� 
     * @param  byte[] key  ��/����Ҫ�õĳ���Ϊ32���ֽ����飨256λ����Կ 
     * @return byte[]  ���ܺ���ֽ����� 
     */  
    public static byte[] Aes256Encode(String str, byte[] key){  
        byte[] result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //���ɼ��ܽ�����Ҫ��Key  
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);  
            result = cipher.doFinal(str.getBytes("UTF-8"));  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    /** 
     * @param  byte[] bytes  Ҫ�����ܵ��ֽ����� 
     * @param  byte[] key    ��/����Ҫ�õĳ���Ϊ32���ֽ����飨256λ����Կ 
     * @return String  ���ܺ���ַ��� 
     */  
    public static String Aes256Decode(byte[] bytes, byte[] key){  
        String result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //���ɼ��ܽ�����Ҫ��Key  
            cipher.init(Cipher.DECRYPT_MODE, keySpec);  
            byte[] decoded = cipher.doFinal(bytes);  
            result = new String(decoded, "UTF-8");  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
}
