package com.amazonaws.lambda.demo.http;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import com.amazonaws.lambda.demo.model.Member;
import com.amazonaws.lambda.demo.model.Choice;
import com.amazonaws.lambda.demo.model.Alternative;


public class CreateChoiceRequest {
	public String choiceId;
	public String description;
	public ArrayList<Alternative> alternatives;
	public ArrayList<Member> members;
	public int numMembers;
	public boolean isCompleted;
	public Date dateCompleted;
	public Alternative altChosen;
	
	public CreateChoiceRequest() {}
	
	public CreateChoiceRequest(String desc, ArrayList<Alternative> alts, int numM) {
		choiceId = UUID.randomUUID().toString();
		this.description = desc;
		this.alternatives = alts;
		this.members = new ArrayList<>();
		this.numMembers = numM;
		this.isCompleted = false;
		this.dateCompleted = null;
		this.altChosen = null;
	}
	
	public String toString() {
		return "CreateChoice(" + choiceId + "," + description + ")";
	}
	
	
	
	
	
	
	
}
