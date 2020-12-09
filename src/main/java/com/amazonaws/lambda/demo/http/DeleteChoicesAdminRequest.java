package com.amazonaws.lambda.demo.http;

import java.sql.Date;

public class DeleteChoicesAdminRequest {
	
	public long days;
	
	public DeleteChoicesAdminRequest() {
		days = 0;
	}
	
	public DeleteChoicesAdminRequest(long days) {
		this.days = days;
	}
	
	
	public String toString() {
		return "DeleteChoicesAdminRequest(" + this.days + ")";
	}

}
