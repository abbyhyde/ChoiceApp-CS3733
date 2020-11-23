package com.amazonaws.lambda.demo.http;

public class ParticipateChoiceRequest {
	
	public String choiceId;
	public String memberName;
	public String pass;
	
	public ParticipateChoiceRequest() {
		
	}
	
	public ParticipateChoiceRequest(String cid, String name) {
		choiceId = cid;
		memberName = name;
		pass = null;
	}
	
	public ParticipateChoiceRequest(String cid, String name, String password) {
		choiceId = cid;
		memberName = name;
		pass = password;
	}
	
	public String toString() {
		return "ParticipateChoice(" + choiceId + "," + memberName + ")";
	}

	public String getChoiceId() {return choiceId;}
	public void setChoiceId(String ci) {this.choiceId = ci;}

	public String getMemberName() {return memberName;}
	public void setMemberName(String mn) {this.memberName = mn;}

	public String getPass() {return pass;}
	public void setPass(String p) {this.pass = p;}


}
