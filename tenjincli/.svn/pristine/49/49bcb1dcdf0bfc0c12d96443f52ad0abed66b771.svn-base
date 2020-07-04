package com.ycs.tenjincli.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.ycs.tenjin.security.Authenticator;
import com.ycs.tenjin.security.TenjinSecurityException;
import com.ycs.tenjincli.util.Utilities;

public class RestClient {
	
	public static RestResponse post(String basePath, String resource, Form form) throws RestException {
		try {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(basePath).path(resource);
			Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
			
			int status = response.getStatus();
			String rawContent = response.readEntity(String.class);
			
			JSONObject json = new JSONObject(rawContent);
			String message = json.getString("message");
			
			String content = "";
			try{
				content = json.getString("data");
			}catch(JSONException ignore) {}
			
			return RestResponse.create(status, message, content);
			
		} 
		catch (Exception e) {
			/*Changed by padmavathi for T25IT-12 starts*/
			//throw new RestException("Could not invoke service at " + basePath + resource + ". This could be due to an unexpected error while calling the service. Please contact Tenjin Support.");
			String message = e.getMessage();
			if(Utilities.trim(message).toLowerCase().contains("URI is not absolute".toLowerCase()) || Utilities.trim(message).toLowerCase().contains("Connection refused".toLowerCase())) {
				return RestResponse.create(404, "The Tenjin URL you have entered is invalid.", null);
			}else{
				throw new RestException("Could not invoke service at " + basePath + resource + ". This could be due to an unexpected error while calling the service. Please contact Tenjin Support.");
			}
		}
		/*Changed by padmavathi for T25IT-12 ends*/
	}
	
	public static RestResponse post(String basePath, String resource, String authToken, String postData) throws RestException {
		try{
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(basePath).path(resource);
			
			Response response = target.request(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + authToken).post(Entity.entity(postData, MediaType.APPLICATION_JSON), Response.class);
			
			int status = response.getStatus();
			String rawContent = response.readEntity(String.class);
			
			JSONObject json = new JSONObject(rawContent);
			String message = json.getString("message");
			
			String content = "";
			try{
				content = json.getString("data");
			}catch(JSONException ignore) {}
			
			return RestResponse.create(status, message, content);
		} catch(Exception e) {
			throw new RestException("Could not invoke service at " + basePath + resource + ". This could be due to an unexpected error while calling the service. Please contact Tenjin Support.");
		}
	}
	
	public static RestResponse post(String basePath, String resource, String authToken, String postData, String contentType, String accept) throws RestException {
		try{
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(basePath).path(resource);
			
			Response response = target.request(contentType)
					.header("Authorization", "Basic " + authToken)
					.header("Accept", accept)
					.post(Entity.entity(postData, contentType), Response.class);
			
			int status = response.getStatus();
			String rawContent = response.readEntity(String.class);
			
			JSONObject json = new JSONObject(rawContent);
			String message = json.getString("message");
			
			String content = "";
			try{
				content = json.getString("data");
			}catch(JSONException ignore) {}
			
			return RestResponse.create(status, message, content);
		} catch(Exception e) {
			throw new RestException("Could not invoke service at " + basePath + resource + ". This could be due to an unexpected error while calling the service. Please contact Tenjin Support.");
		}
	}

	
	public static RestResponse get(String basePath, String resource, String token) throws RestException {
		
		try {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(basePath).path(resource);
			Response response = target.request().header("Authorization", "Basic " + token).get();
			
			int status = response.getStatus();
			String rawContent = response.readEntity(String.class);
			
			JSONObject json = new JSONObject(rawContent);
			String message = json.getString("message");
			
			String content = "";
			try{
				content = json.getString("data");
			}catch(JSONException ignore) {}
			
			return RestResponse.create(status, message, content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RestException("Could not invoke service at " + basePath + resource + ". This could be due to an unexpected error while calling the service. Please contact Tenjin Support.", e);
		}
		
	}
	
}
