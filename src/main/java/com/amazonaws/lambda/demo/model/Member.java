package com.amazonaws.lambda.demo.model;

public class Member{
	
	public String name;
	String pass;
	
	public Member() {}
	
	public Member(String name, String pass) {
		this.name = name;
		this.pass = pass;
	}
	
	public Member(String name) {
		this.name = name;
		pass = null;
	}
	
	public boolean equals(Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Member) {
			Member other = (Member) o;
			if(pass != null) {
				return (name.equals(other.name)) && (pass.equals(other.pass));
			}
			else {
				return name.equals(other.name);
			}
		}
		
		return false;  // not a member
	}
	
	public String toString() {
		return "Member(" + name + ")";
	}
	
	public String getPass() { return pass; } 
	public void setPass(String password) { this.pass = password; }

	
	
	
}
