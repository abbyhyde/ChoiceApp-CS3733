
package com.amazonaws.lambda.demo.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.demo.model.Alternative;
import com.amazonaws.lambda.demo.model.Choice;
import com.amazonaws.lambda.demo.model.Member;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Constants" then it must be "Constants" in the SQL queries.
 * 
 * @author heineman
 *
 */
public class ChoiceDAO { 
	

	java.sql.Connection conn;
	
	final String tblAlt = "Alternatives";
	final String tblApprovers = "Approvers";
	final String tblChoices = "Choices";
	final String tblDisapprovers = "Disapprovers";
	final String tblFeedback = "Feedbacks";
	final String tblMembers = "Members";

    public ChoiceDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public Choice getChoice(String choiceId) throws Exception {
        
        try {
            Choice choice = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblChoices + " WHERE choiceId=?;");
            ps.setString(1,  choiceId);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                choice = generateChoice(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return choice;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting choice: " + e.getMessage());
        }
    }
   

    public boolean addChoice(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblChoices + " WHERE choiceId = ?;");
            ps.setString(1, choice.choiceId);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Choice c = generateChoice(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblChoices + " (choiceId,description,maxNumMembers,isCompleted,dateCompleted,chosenAlt) VALUES(?,?,?,?,?,?);");
            ps.setString(1,  choice.choiceId);
            ps.setString(2,  choice.description);
            ps.setInt(3, choice.maxNumMembers);
            ps.setBoolean(4, choice.isCompleted);
            ps.setDate(5, choice.dateCompleted);
            ps.setString(6, "");
            ps.execute();
            
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert choice: " + e.getMessage());
        }
    }
    
    public boolean addMember(Choice choice, Member member) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblMembers + " WHERE memberId = ?;");
            String memberId = (member.name + choice.choiceId);
            ps.setString(1, memberId);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
            	if (resultSet.getString("password") == null) {
            		return true;
            	}
            	if (member.getPass().equals(resultSet.getString("password"))) {
            		return true;
            	} 
                resultSet.close();
                return false; // password exists but doesn't match
            }

            if (choice.numCurrentMembers() < choice.maxNumMembers) {
            	ps = conn.prepareStatement("INSERT INTO " + tblMembers + " (memberId, choiceId, name, password) values(?,?,?,?);");
            	ps.setString(1,  memberId);
            	ps.setString(2,  choice.choiceId);
            	ps.setString(3,  member.name);
            	ps.setString(4,  member.getPass());
                ps.execute();
                
                choice.addMember(member);
                return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
    
    /*
    public boolean updateChoice(Choice choice) throws Exception {
        try {
        	//(isCompleted,dateCompleted,chosenAlt) values(?,?,?,?,?,?);");
        	String query = "UPDATE " + tblChoices + " SET value=? WHERE choiceId=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setObject(1, choice);
        	ps.setString(2, choice.choiceId);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update choice: " + e.getMessage());
        }
    }
    */

    public Alternative getAlt(String altId) throws Exception {
        
        try {
            Alternative alt = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblAlt + " WHERE altId=?;");
            ps.setString(1,  altId);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                alt = generateAlt(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return alt;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting alternative: " + e.getMessage());
        }
    }


    
    private Choice generateChoice(ResultSet resultSet) throws Exception {
    	Choice choice = new Choice();
        choice.choiceId = resultSet.getString("choiceId");
        choice.description = resultSet.getString("description");
        choice.maxNumMembers = resultSet.getInt("maxNumMembers");
        choice.isCompleted = resultSet.getBoolean("isCompleted");
        choice.dateCompleted = resultSet.getDate("dateCompleted");
        
        choice.altChosen = getAlt(resultSet.getString("chosenAlt"));
        
        choice.alternatives = getAltsFromChoice(choice.choiceId);
        choice.members = getMembersFromChoice(choice.choiceId);
        return choice;
    }
    
    private Alternative generateAlt(ResultSet resultSet) throws Exception {
    	//not actually the full alternative, as we don't need to get the approvals and feedback ect.
        Alternative alt = new Alternative();
        alt.description = resultSet.getString("description");
        
        //alt.approvers
        //resultSet.getObject("value");
        return alt;
    }
    
    private Member generateMember(ResultSet resultSet) throws Exception {
    	Member member = new Member();
        member.name = resultSet.getString("name");
        member.setPass(resultSet.getString("password"));
        
        return member;
    }
    
    private ArrayList<Alternative> getAltsFromChoice(String choiceId) throws Exception{
    	try {
            Alternative currentAlt = null;
            ArrayList<Alternative> alts = new ArrayList<Alternative>();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblAlt + " WHERE choiceId=?;");
            ps.setString(1,  choiceId);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                currentAlt = generateAlt(resultSet);
                alts.add(currentAlt);
            }
            resultSet.close();
            ps.close();
            
            return alts;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting alternatives: " + e.getMessage());
        }
    	
    }
    
    private ArrayList<Member> getMembersFromChoice(String choiceId) throws Exception{
    	try {
            Member currentMember = null;
            ArrayList<Member> members = new ArrayList<Member>();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblMembers + " WHERE choiceId=?;");
            ps.setString(1,  choiceId);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                currentMember = generateMember(resultSet);
                members.add(currentMember);
            }
            resultSet.close();
            ps.close();
            
            return members;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting members: " + e.getMessage());
        }
    	
    }

}