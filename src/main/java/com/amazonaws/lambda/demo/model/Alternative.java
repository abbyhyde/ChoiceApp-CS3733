package com.amazonaws.lambda.demo.model;

import java.util.ArrayList;

public class Alternative{
	
	public String description;
	public ArrayList<Member> approvers;
	public ArrayList<Member> disapprovers;
	public ArrayList<Feedback> feedbacks;
	
	public Alternative() {
		this.approvers = new ArrayList<Member>();
		this.disapprovers = new ArrayList<Member>();
		this.feedbacks = new ArrayList<Feedback>();
	}
	
	public Alternative(String d) {
		this.description = d;
		this.approvers = new ArrayList<Member>();
		this.disapprovers = new ArrayList<Member>();
		this.feedbacks = new ArrayList<Feedback>();
	}
	
	public boolean equals(Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Alternative) {
			Alternative other = (Alternative) o;
			return description.equals(other.description);
		}
		
		return false;  // not an Alt
	}
	
	public void addApprove(Member m) {
		approvers.add(m);
	}
	
	public void removeApprove(Member m) {
		approvers.remove(m);
	}
	
	public void addDisapprove(Member m) {
		disapprovers.add(m);
	}
	
	public void removeDisapprove(Member m) {
		disapprovers.remove(m);
	}
	
	public void addFeedback(Feedback feedback) {
		feedbacks.add(feedback);
		
	}
	
	public boolean containsApprover(Member m) {
		for(Member mem : approvers) {
			if(mem.name.equals(m.name)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsDisapprover(Member m) {
		for(Member mem : disapprovers) {
			if(mem.name.equals(m.name)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "Alternative(" + description + ")";
	}

	
	
}
