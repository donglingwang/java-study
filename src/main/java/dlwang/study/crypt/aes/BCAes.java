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
		// 生成KEY
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
     * @param  String str  要被加密的字符串 
     * @param  byte[] key  加/解密要用的长度为32的字节数组（256位）密钥 
     * @return byte[]  加密后的字节数组 
     */  
    public static byte[] Aes256Encode(String str, byte[] key){  
        byte[] result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key  
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);  
            result = cipher.doFinal(str.getBytes("UTF-8"));  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    /** 
     * @param  byte[] bytes  要被解密的字节数组 
     * @param  byte[] key    加/解密要用的长度为32的字节数组（256位）密钥 
     * @return String  解密后的字符串 
     */  
    public static String Aes256Decode(byte[] bytes, byte[] key){  
        String result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key  
            cipher.init(Cipher.DECRYPT_MODE, keySpec);  
            byte[] decoded = cipher.doFinal(bytes);  
            result = new String(decoded, "UTF-8");  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
}
