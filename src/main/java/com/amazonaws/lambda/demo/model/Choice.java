package com.amazonaws.lambda.demo.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;


public class Choice {
	public String choiceId;
	public String description;
	public ArrayList<Alternative> alternatives;
	public ArrayList<Member> members;
	public int numMembers; //maxNumMember
	public boolean isCompleted;
	public Date dateCreated;
	public Alternative altChosen;
	
	public Choice() {}
	
	public Choice(String choiceID, String desc, ArrayList<Alternative> alts, int numM) {
		choiceId = choiceID;
		this.description = desc;
		this.alternatives = alts;
		this.members = new ArrayList<>();
		this.numMembers = numM;
		this.isCompleted = false;
		this.dateCreated = new Date(System.currentTimeMillis());
		this.altChosen = null;
	}
	
	public boolean addMember(Member m) {
		this.members.add(m);
		return true;		
	}
	
	public Alternative getAlt(String desc) {
		for(Alternative a : alternatives) {
			if(a.description.equals(desc)) {
				return a;
			}
		}
		return null;
	}
	
	
	public boolean equals(Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Choice) {
			Choice other = (Choice) o;
			return choiceId.equals(other.choiceId);
		}
		
		return false;  // not a Choice
	}
	
	public String toString() {
		return "Choice(" + choiceId + "," + description + ")";
	}
	
	public int numCurrentMembers() {
		return members.size();
	}
	
	public boolean containsMember(Member m) {
		return members.contains(m);
	}
	
}

