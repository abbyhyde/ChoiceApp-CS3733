package com.amazonaws.lambda.demo.http;

import java.util.ArrayList;

import com.amazonaws.lambda.demo.model.Choice;

public class DeleteChoicesAdminResponse {
	public final ArrayList<Choice> choices;
	public final int httpCode;
	
	public DeleteChoicesAdminResponse (ArrayList<Choice> cs, int code) {
		this.choices = cs;
		this.httpCode = code;
	}
	
	// 200 means success
	public DeleteChoicesAdminResponse (ArrayList<Choice> cs) {
		this.choices = cs;
		this.httpCode = 200;
	}
	
	public DeleteChoicesAdminResponse (int code) {
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
