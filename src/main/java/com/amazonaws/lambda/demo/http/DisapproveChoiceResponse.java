package com.amazonaws.lambda.demo.http;

import com.amazonaws.lambda.demo.model.Choice;

public class DisapproveChoiceResponse {
	public final Choice choice;
	public final int httpCode;
	
	public DisapproveChoiceResponse (Choice c, int code) {
		this.choice = c;
		this.httpCode = code;
	}
	
	// 200 means success
	public DisapproveChoiceResponse (Choice c) {
		this.choice = c;
		this.httpCode = 200;
	}
	
	public DisapproveChoiceResponse (int code) {
		this.choice = null;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + choice + ")";
	}
}

