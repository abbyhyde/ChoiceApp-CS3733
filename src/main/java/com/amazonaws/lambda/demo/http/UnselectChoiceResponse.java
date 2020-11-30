package com.amazonaws.lambda.demo.http;

import com.amazonaws.lambda.demo.model.Choice;

public class UnselectChoiceResponse {
	public final Choice choice;
	public final int httpCode;
	
	public UnselectChoiceResponse (Choice c, int code) {
		this.choice = c;
		this.httpCode = code;
	}
	
	// 200 means success
	public UnselectChoiceResponse (Choice c) {
		this.choice = c;
		this.httpCode = 200;
	}
	
	public UnselectChoiceResponse (int code) {
		this.choice = null;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + choice + ")";
	}
}

