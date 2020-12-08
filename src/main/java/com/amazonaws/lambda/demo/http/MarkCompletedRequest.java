package com.amazonaws.lambda.demo.http;

public class MarkCompletedRequest {
	
	public String choiceId;
	public String altDesc;
	
	public MarkCompletedRequest() {
	}
	
	public MarkCompletedRequest(String cid, String altD) {
		choiceId = cid;
		altDesc = altD;
	}
	
	
	public String toString() {
		return "MarkCompletedRequest(" + choiceId + "," + altDesc + ", ";
	}

	public String getChoiceId() {return choiceId;}
	public void setChoiceId(String ci) {this.choiceId = ci;}

	public String getAltDesc() {return altDesc;}
	public void setAltDesc(String ad) {this.altDesc = ad;}
}
