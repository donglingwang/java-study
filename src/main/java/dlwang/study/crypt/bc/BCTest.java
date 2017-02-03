package dlwang.study.crypt.bc;

import java.security.MessageDigest;
import java.security.Security;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class BCTest {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static void md4() throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD4","BC");
		md.update("wangdongling".getBytes());
		
		byte [] result = md.digest();
		
		System.out.println(org.apache.commons.codec.binary.Hex.encodeHexString(result));
		
		System.out.println(new String(Hex.encode(result)));
	}
	
	public static void base64() {
		byte [] input = ",,,+++£¬     £¬£¬\n\n\n\t\t".getBytes();
		byte [] data = org.bouncycastle.util.encoders.Base64.encode(input);
		System.out.println(new String(data));
		byte [] result = org.bouncycastle.util.encoders.Base64.decode(data);
		System.out.println(new String(result));
	}
	
	public static void hex() {
		String str = "Hex ±àÂë";
		byte [] data = Hex.encode(str.getBytes());
		
		System.out.println(new String(data));
		
		byte [] result = Hex.decode(data);
		System.out.println(new String(result));
		//System.out.println(Base64.encodeBase64String(data));
	}
	
	public static void main(String[] args) {
		try {
			md4();
			//base64();
			//hex();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
