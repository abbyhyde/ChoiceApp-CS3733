package com.amazonaws.lambda.demo.model;

import java.sql.Date;

public class Feedback{
	
	Date timeMade;
	Member member;
	String contents;
	
	public Feedback(Member m, String c) {
		this.member = m;
		this.contents = c;
		this.timeMade = new Date(System.currentTimeMillis());
	}
	
	public boolean equals(Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Feedback) {
			Feedback other = (Feedback) o;
			return (contents.equals(other.contents)) && (member.equals(other.member));
		}
		
		return false;  // not a Feedback
	}
	
	public String toString() {
		return "Feedback(" + member.name + ": " + contents + ")";
	}

}
