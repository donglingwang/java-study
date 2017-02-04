package dlwang.study.crypt.servlet;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;

import dlwang.study.crypt.cipher.AESCoder;
import dlwang.study.crypt.cipher.RSACoder;
import dlwang.study.main.Main;
import dlwang.study.util.JacksonUtils;

public class CryptUtil {

	public static String RSA_PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOrnGtk7n4ejDFOhd4ep2NLdpqqCSH2CgC38SBaHICmwMj38eq8cPOTAFw3KEAEQGD3Dc2THetbFAPHZsuvoPqkCAwEAAQ==";
	
	public static String RSA_PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEA6uca2Tufh6MMU6F3h6nY0t2mqoJIfYKALfxIFocgKbAyPfx6rxw85MAXDcoQARAYPcNzZMd61sUA8dmy6+g+qQIDAQABAkEAyxtMUayGAadvTxh726d2PM0+AIxp1WvzHM/YHPJ1UtTIRsCSgKH4Mtq2X0RqqhJcgZ7jQlrgnjWriIDrOWUcAQIhAPud2P8GpDXlpsbmkHO6aBQDTGh66gakeiVRrhHW2bDpAiEA7v63o85TNp5Ma7q3IWthbepgweJBsn/qC3VSkGInh8ECIHvcHwajOVCCxQ9iTjd5ymQXn5RK9UrhynL2e06yyaSJAiBLhRe4XkkzOCBD5nNZzxmxZt9+RKWn5v2o80DMuQduwQIgaE/CBkf7PFYvzL7UztjfD7Dr7zs7MNXl24YNA1z0ptc=";
	/*static {
		try {
			Map<String, Object> keyMap = RSACoder.initKey();
			RSA_PUBLIC_KEY = Base64.encodeBase64String(RSACoder.getPublicKey(keyMap));
			RSA_PRIVATE_KEY = Base64.encodeBase64String(RSACoder.getPrivateKey(keyMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	@Test
	public void test() throws Exception {
		//System.out.println(RSA_PRIVATE_KEY);
		//System.out.println(RSA_PUBLIC_KEY);
		String input = "hello aes and rsa";
		byte [] aesKey = AESCoder.initKey();
		byte [] data = AESCoder.encrypt(input.getBytes(), aesKey);
		System.out.println("aesParam£º"+Hex.encodeHexString(data));
		byte [] data2 = Hex.decodeHex(Hex.encodeHexString(data).toCharArray());
		assertArrayEquals(data, data2);
		byte [] rsaData = RSACoder.encryptByPrivateKey(aesKey, RSA_PRIVATE_KEY);
		System.out.println("rsaKey£º" + Hex.encodeHexString(rsaData));
		
		/*String input = "rsa";
		byte [] data = RSACoder.encryptByPrivateKey(input.getBytes(), RSA_PRIVATE_KEY);
		byte [] result = RSACoder.decryptByPublicKey(data, RSA_PUBLIC_KEY);
		System.out.println(new String(result));*/
		
	}
	
	@Test
	public void postCrypt() throws Exception {
		String url = "http://localhost:8081/java-study/cryptServlet";
		String input = "hello aes and rsa";

		byte [] aesKey = AESCoder.initKey();
		byte [] data = AESCoder.encrypt(input.getBytes(), aesKey);
		System.out.println("aesParam£º"+Hex.encodeHexString(data));
		byte [] rsaData = RSACoder.encryptByPrivateKey(aesKey, RSA_PRIVATE_KEY);
		System.out.println("rsaKey£º" + Hex.encodeHexString(rsaData));
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("aesParam", Hex.encodeHexString(data));
		map.put("rsaKey", Hex.encodeHexString(rsaData));
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("aesParam", Hex.encodeHexString(data)));
		nvps.add(new BasicNameValuePair("rsaKey", Hex.encodeHexString(rsaData)));
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom()  
			    .setConnectionRequestTimeout(5000)
			    .setConnectTimeout(5000)  
			    .setSocketTimeout(5000).build(); 
		httpPost.setConfig(requestConfig);
		
		HttpEntity httpEntity = new UrlEncodedFormEntity(nvps);
		httpPost.setEntity(httpEntity);
		
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse(
					final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					//InputStream stream = entity.getContent();
					//byte [] result = new byte[stream.available()];
					//stream.read(result);
					//System.out.println("here :"+new String(result));
					if (entity != null) {
						BufferedHttpEntity entity2 = new BufferedHttpEntity(entity);
						System.out.println("here:"+EntityUtils.toString(entity2));
						return EntityUtils.toString(entity2);
					}
					return null;
					//return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}
		};
		
		String responseBody = httpClient.execute(httpPost, responseHandler);
		System.out.println(responseBody);
		
	}
}
