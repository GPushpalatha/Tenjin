package com.ycs.tenjincli.rest;

public class RestResponse {
	private int status;
	private String message;
	private String content;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public static RestResponse create(int status, String message, String content) {
		RestResponse resp = new RestResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setContent(content);
		
		return resp;
	}
}
