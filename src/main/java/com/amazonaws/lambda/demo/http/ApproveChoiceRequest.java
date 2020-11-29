package com.amazonaws.lambda.demo.http;

public class ApproveChoiceRequest {
	
	public String choiceId;
	public String memberName;
	public String altDesc;
	
	public ApproveChoiceRequest() {
		
	}
	
	public ApproveChoiceRequest(String cid, String name, String altd) {
		choiceId = cid;
		memberName = name;
		altDesc = altd;
	}
	
	
	public String toString() {
		return "ParticipateChoice(" + choiceId + "," + memberName + ")";
	}

	public String getChoiceId() {return choiceId;}
	public void setChoiceId(String ci) {this.choiceId = ci;}

	public String getMemberName() {return memberName;}
	public void setMemberName(String mn) {this.memberName = mn;}

	public String getAltDesc() {return altDesc;}
	public void setAltDesc(String ad) {this.altDesc = ad;}


}
