package com.ycs.tenjin.security;

import com.ycs.tenjincli.rest.RestClient;
import com.ycs.tenjincli.rest.RestException;
import com.ycs.tenjincli.rest.RestResponse;
import com.ycs.tenjincli.util.Utilities;

import javax.ws.rs.core.Form;

import org.apache.commons.codec.binary.Base64;



public class Authenticator {
	
	/*public static void authenticate() throws TenjinSecurityException, RestException{
		String baseUrl = Utilities.getProperty("BASE_URL");
		String userId = Utilities.getProperty("USER_NAME");
		String password = Utilities.getProperty("USER_KEY");
		
		if(baseUrl.length() < 1 || userId.length() < 1 || password.length() < 1) {
			throw new TenjinSecurityException("Tenjin Server configuration is incomplete. Please use the connect command to update configuration.");
		}
		String decryptedPassword = "";
		try {
			decryptedPassword = new String(Base64.decodeBase64(password.getBytes()));
			if(decryptedPassword.contains(":")) {
				String[] d = decryptedPassword.split(":");
				decryptedPassword = d[1];
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TenjinSecurityException("ERROR logging in to Tenjin. Please contact Tenjin Support.");
		} 
		
		Form form = new Form();
		form.param("username", userId);
		form.param("password", decryptedPassword);
		
		
		RestResponse response = RestClient.post(baseUrl, "/rest/v1/authentication", form);
		if(response.getStatus() != 200) {
			throw new TenjinSecurityException(response.getMessage());
		}
		
	}*/
	
	public static String authenticate(String baseUrl, String userName, String password) throws TenjinSecurityException, RestException {
		
		/*Changed by padmavathi for T25IT-12 starts*/
		/*if(baseUrl.length() < 1 || userName.length() < 1 || password.length() < 1) {
			throw new TenjinSecurityException("Tenjin Server configuration is incomplete. Please use the connect command to update configuration.");
		}*/
		/*Changed by padmavathi for T25IT-12 ends*/
		Form form = new Form();
		form.param("username", userName);
		form.param("password", password);
		
		
		RestResponse response = RestClient.post(baseUrl, "/rest/v1/authentication", form);
		if(response.getStatus() != 200) {
			throw new TenjinSecurityException(response.getMessage());
		}
		else{
			return response.getContent();
		}
		
	}
	
	public static boolean isAuthenticated() {
		return true;
	}
	
}
