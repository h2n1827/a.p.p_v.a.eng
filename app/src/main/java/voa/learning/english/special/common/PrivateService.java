package voa.learning.english.special.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import android.util.Log;

public class PrivateService {
	public static final String PRIVATE_KEY = "AICTICzaSyNCiALt50BufJVuiefe";

	public String securityPrivate(String url) {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(url.getBytes());

			byte byteData[] = md.digest();
			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();

		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	
	public static String etavirplru(String var) {
		String string = var.substring(1, var.length()-2);		
		String value = "";
		// Get bytes from string
		byte[] byteArray;
		try {
			byteArray = kkScure.decode(string.getBytes());
			value = new String(byteArray, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}	
	public static String decodevirp(String var) {
		String string = var;		
		String value = "";
		// Get bytes from string
		byte[] byteArray;
		try {
			byteArray = kkScure.decode(string.getBytes());
			value = new String(byteArray, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}	
	public static String encodevirp(String text) {	 
	    // Sending side
	    byte[] data = null;
	    try {
	        data = text.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e1) {
	    e1.printStackTrace();
	    }
	    String base64 = kkScure.encodeBytes(data);	    
	    return base64;
	}

}
