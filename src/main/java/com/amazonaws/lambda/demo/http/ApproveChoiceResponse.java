package com.amazonaws.lambda.demo.http;

import com.amazonaws.lambda.demo.model.Choice;

public class ApproveChoiceResponse {
	public final Choice choice;
	public final int httpCode;
	
	public ApproveChoiceResponse (Choice c, int code) {
		this.choice = c;
		this.httpCode = code;
	}
	
	// 200 means success
	public ApproveChoiceResponse (Choice c) {
		this.choice = c;
		this.httpCode = 200;
	}
	
	public ApproveChoiceResponse (int code) {
		this.choice = null;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + choice + ")";
	}
}

