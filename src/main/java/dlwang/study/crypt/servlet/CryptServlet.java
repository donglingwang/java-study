package dlwang.study.crypt.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import dlwang.study.crypt.cipher.AESCoder;
import dlwang.study.crypt.cipher.RSACoder;

/**
 * Servlet implementation class CryptServlet
 */
public class CryptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CryptServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 1、使用rsa公钥加密后的密钥
		String rsaKey = request.getParameter("rsaKey");
		// 2、使用aes加密后的数据
		String aesParam = request.getParameter("aesParam");
		
		try {
			// 3、使用RSA私钥进行解密得到aes密钥
			byte [] aesKey = RSACoder.decryptByPublicKey(Hex.decodeHex(rsaKey.toCharArray()), CryptUtil.RSA_PUBLIC_KEY);
			// 4、使用aes密钥解密上传的数据
			byte [] result = AESCoder.decrypt(Hex.decodeHex(aesParam.toCharArray()), aesKey);
			response.getWriter().write("*******"+new String(result)+"******");
			response.getWriter().flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//doGet(request, response);
	}

}
