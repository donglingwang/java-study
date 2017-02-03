package dlwang.study.util;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/** 
 * JSON utils
 * @author 
 *
 */
public class JacksonUtils {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static <T> T stringToObject(String str, Class<T> clazz){
		try {
			return objectMapper.readValue(str, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String objectToString(Object obj){
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T streamToObject(InputStream istream, Class<T> clazz){
		try {
			return objectMapper.readValue(istream, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonNode streamToJsonNode(InputStream inputStream){
		try {
			return objectMapper.readTree(inputStream);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ObjectMapper getObjectMapper(){
		return objectMapper;
	}
}
