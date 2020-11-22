package com.amazonaws.lambda.demo.http;

public class CreateChoiceResponse {
	public final String response;
	public final int httpCode;
	
	public CreateChoiceResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public CreateChoiceResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}

