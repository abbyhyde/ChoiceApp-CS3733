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
	public int numMembers;
	
	public CreateChoiceRequest() {}
	
	public CreateChoiceRequest(String desc, ArrayList<Alternative> alts, int numM) {
		choiceId = UUID.randomUUID().toString();
		this.description = desc;
		this.alternatives = alts;
		this.numMembers = numM;
	}
	
	public String toString() {
		return "CreateChoice(" + choiceId + "," + description + ")";
	}
	
	public String getChoiceId() { return choiceId; }
	public void setChoiceId(String ci) { this.choiceId = ci; }
	
	public String getDescription() { return description; }
	public void setDescription(String d) { this.description = d; }
	
	public ArrayList<Alternative> getAlternatives() { return alternatives; }
	public void setAlternatives(ArrayList<Alternative> alts) { this.alternatives = alts; }
	
	public int getNumMembers() { return numMembers; }
	public void setNumMembers(int nm) { this.numMembers = nm; }
	
	
	
	
	
	
	
	
}
