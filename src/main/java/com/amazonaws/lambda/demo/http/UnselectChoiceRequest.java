package com.amazonaws.lambda.demo.http;

public class UnselectChoiceRequest {

	public String choiceId;
	public String memberName;
	public String altDesc;
	
	public UnselectChoiceRequest() {
		
	}
	
	public UnselectChoiceRequest(String cid, String name, String altD) {
		choiceId = cid;
		memberName = name;
		altDesc = altD;
	}
	
	
	public String toString() {
		return "UnselectChoice(" + choiceId + "," + memberName + "," + altDesc + ")";
	}

	public String getChoiceId() {return choiceId;}
	public void setChoiceId(String ci) {this.choiceId = ci;}

	public String getMemberName() {return memberName;}
	public void setMemberName(String mn) {this.memberName = mn;}

	public String getAltDesc() {return altDesc;}
	public void setAltDesc(String ad) {this.altDesc = ad;}
}