package com.amazonaws.lambda.demo.model;

import java.sql.Date;

public class Feedback{
	
	public Date timeMade;
	public Member member;
	public String contents;
	
	public Feedback(Member m, String c, Date d) {
		this.member = m;
		this.contents = c;
		this.timeMade = d;
	}
	
	public Feedback() {}
	
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
