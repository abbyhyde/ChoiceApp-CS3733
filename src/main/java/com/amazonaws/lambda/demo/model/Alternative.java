package com.amazonaws.lambda.demo.model;

import java.util.ArrayList;

public class Alternative{
	
	String description;
	ArrayList<Member> approvers;
	ArrayList<Member> disapprovers;
	ArrayList<Feedback> feedbacks;
	
	public Alternative(String d) {
		this.description = d;
		this.approvers = new ArrayList<>();
		this.disapprovers = new ArrayList<>();
		this.feedbacks = new ArrayList<>();
	}
	
	public boolean equals(Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Alternative) {
			Alternative other = (Alternative) o;
			return description.equals(other.description);
		}
		
		return false;  // not an Alt
	}
	
	public String toString() {
		return "Alternative(" + description + ")";
	}
	
}