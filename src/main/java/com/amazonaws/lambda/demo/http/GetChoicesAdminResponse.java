package com.amazonaws.lambda.demo.http;

import java.util.ArrayList;

import com.amazonaws.lambda.demo.model.Choice;

public class GetChoicesAdminResponse {
	public final ArrayList<Choice> choices;
	public final int httpCode;
	
	public GetChoicesAdminResponse (ArrayList<Choice> cs, int code) {
		this.choices = cs;
		this.httpCode = code;
	}
	
	// 200 means success
	public GetChoicesAdminResponse (ArrayList<Choice> cs) {
		this.choices = cs;
		this.httpCode = 200;
	}
	
	public GetChoicesAdminResponse (int code) {
		this.choices = null;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + choices + ")";
	}
	
	public void addChoice(Choice c) {
		this.choices.add(c);
	}
}

