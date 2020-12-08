package com.amazonaws.lambda.demo.http;

import com.amazonaws.lambda.demo.model.Choice;

public class MarkCompletedResponse {
	public final Choice choice;
	public final int httpCode;
	
	public MarkCompletedResponse (Choice c, int code) {
		this.choice = c;
		this.httpCode = code;
	}
	
	// 200 means success
	public MarkCompletedResponse (Choice c) {
		this.choice = c;
		this.httpCode = 200;
	}
	
	public MarkCompletedResponse (int code) {
		this.choice = null;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + choice + ")";
	}
}
