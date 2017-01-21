package dlwang.study.crypt.md5;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class MD5Util {

    private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };
    
    /**
     * 将byte数组转换为16进制
     * >>> 逻辑右移或叫无符号右移运算符“>>>“只对位进行操作，没有算术含义，它用0填充左侧的空位
     * byte用二进制表示占用8位，而我们知道16进制的每个字符需要用4位二进制位来表示，
     * 所以我们就可以把每个byte转换成两个相应的16进制字符，即把byte的高4位和低4位分别转换成相应的16进制字符H和L
     * @param md
     * @return
     */
    public static String toHexString(byte[] md){
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
    
    
    public static String bytesToHexString(byte [] src) {
    	StringBuilder builder = new StringBuilder();
    	if (src == null || src.length <= 0) 
    		return null;
    	
    	for (int i = 0; i < src.length; i++) {
    		int v = src[i] & 0xFF;
    		String hv = Integer.toHexString(v);
    		if (hv.length() < 2) {
    			builder.append("0");
    		}
    		builder.append(hv);
    	}
    	return builder.toString();
    }
    
    
    public static byte [] hexStringToBytes(String hexString) {
    	if (hexString == null || "".equals(hexString))
    		return null;
    
    	hexString = hexString.toUpperCase();
    	hexString = hexString.length() % 2 == 0 ? hexString : "0"+hexString;
    	int length = hexString.length() / 2;
    	char [] hexChars = hexString.toCharArray();
    	byte [] d = new byte[length];
    	for (int i = 0; i < length; i++) {
    		int pos = i * 2;
    		d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos+1]));
    	}
    	return d;
    }
    /**
     * 计算字符串的md5值
     * @param str
     * @return
     */
    public static String md5(String str) {
    	
    	try {
    		byte [] strBytes = str.getBytes();
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(strBytes);
			byte [] md = digest.digest();
			return toHexString(md);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 计算文件的md5值
     * linux: md5sum filename
     * @param file
     * @return
     */
    public static String md5(File file) {
    	if (file == null) return null;
    	try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			@SuppressWarnings("resource")
			FileInputStream fis = new FileInputStream(file);
			FileChannel fc = fis.getChannel();
			
			MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			digest.update(buffer);
			byte [] md = digest.digest();
			return toHexString(md);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    
    private static byte charToByte(char c) {
    	return (byte)"0123456789ABCDEF".indexOf(c);
    }
    
    
    public static void main(String[] args) {
		//System.out.println(md5("abc"));
		//System.out.println(md5(new File("src/main/java/dlwang/study/crypt/md5/MD5Util.java")));
    	//System.out.println("0123456789ABCDEF".indexOf('F'));
    	System.out.println(bytesToHexString(hexStringToBytes("abc")));
    }
}
