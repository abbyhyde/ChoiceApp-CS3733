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

}
