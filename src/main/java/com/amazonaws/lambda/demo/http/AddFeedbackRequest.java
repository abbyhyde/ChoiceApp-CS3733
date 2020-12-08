package com.amazonaws.lambda.demo.http;

import java.sql.Date;

public class AddFeedbackRequest {
	
	public String choiceId;
	public String altDesc;
	public String memberName;
	public String feedbackDesc;
	public Date date;
	
	public AddFeedbackRequest() {
		date = new Date(System.currentTimeMillis());
	}
	
	public AddFeedbackRequest(String cid, String altD, String name, String f) {
		choiceId = cid;
		memberName = name;
		altDesc = altD;
		feedbackDesc = f;
		date = new Date(System.currentTimeMillis());
	}
	
	
	public String toString() {
		return "AddFeedbackRequest(" + choiceId + "," + memberName + "," + altDesc + ", " + feedbackDesc + ")";
	}

	public String getChoiceId() {return choiceId;}
	public void setChoiceId(String ci) {this.choiceId = ci;}

	public String getMemberName() {return memberName;}
	public void setMemberName(String mn) {this.memberName = mn;}

	public String getAltDesc() {return altDesc;}
	public void setAltDesc(String ad) {this.altDesc = ad;}
	
	public String getFeedbackDesc() {return feedbackDesc;}
	public void setFeedbackDesc(String fd) {this.feedbackDesc = fd;}
	
	public Date getFeedbackDate() {return date;}
	public void setFeedbackDate(Date d) {}

}
