package com.amazonaws.lambda.demo.http;

public class CreateChoiceResponse {
	public final String choiceId;
	public final int httpCode;
	
	public CreateChoiceResponse (String s, int code) {
		this.choiceId = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public CreateChoiceResponse (String s) {
		this.choiceId = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + choiceId + ")";
	}
}

