
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

    public Choice getChoice(String choiceId, LambdaLogger logger) throws Exception {
    	logger.log("about to get the choice\n");
        try {
            Choice choice = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblChoices + " WHERE choiceId=?;");
            ps.setString(1,  choiceId);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	logger.log("we got a hit\n");
                choice = generateChoice(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return choice;

        } catch (Exception e) {
        	logger.log("couldnt get the choice from the database\n");
        	e.printStackTrace();
            throw new Exception("Failed in getting choice: " + e.getMessage());
        }
    }
    
   

    public boolean addChoice(Choice choice, LambdaLogger logger) throws Exception {
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

            ps = conn.prepareStatement("INSERT INTO " + tblChoices + " (choiceId,description,maxNumMembers,isCompleted,dateCreated,chosenAlt) VALUES(?,?,?,?,?,?);");
            ps.setString(1,  choice.choiceId);
            ps.setString(2,  choice.description);
            ps.setInt(3, choice.numMembers);
            ps.setBoolean(4, choice.isCompleted);
            ps.setDate(5, choice.dateCreated);
            ps.setString(6, "");
            ps.execute();
            
            
            ArrayList<Alternative> alts = choice.alternatives;
            logger.log("\n\n The alts: " + alts);
            int j = 0;
            for(Alternative alt : alts) {
            	if(!alt.description.equals("")) {
            		logger.log("inserting alt " + alt.description);
            		ps = conn.prepareStatement("INSERT INTO " + tblAlt + " (altId, choiceId,description) VALUES(?,?,?);");
            		ps.setString(1,  alt.description + choice.choiceId + j);
            		ps.setString(2,  choice.choiceId);
            		ps.setString(3,  alt.description);
            		ps.execute();
            		j++;
            	}
            	
            }
            
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert choice: " + e.getMessage());
        }
    }
    
    public boolean addMember(Choice choice, Member member, LambdaLogger logger) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblMembers + " WHERE memberId = ?;");
            String memberId = (member.name + choice.choiceId);
            ps.setString(1, memberId);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
            	if ((resultSet.getString("password") == null) && (member.getPass() == null)) {
            		return true;
            	}
            	if (member.getPass().equals(resultSet.getString("password"))) {
            		return true;
            	} 
                resultSet.close();
                return false; // password exists but doesn't match
            }

            if (choice.numCurrentMembers() < choice.numMembers) {
            	logger.log("its trying to add the member\n");
            	ps = conn.prepareStatement("INSERT INTO " + tblMembers + " (memberId, choiceId, name, password) values(?,?,?,?);");
            	ps.setString(1,  memberId);
            	ps.setString(2,  choice.choiceId);
            	ps.setString(3,  member.name);
            	ps.setString(4,  member.getPass());
                ps.execute();
                logger.log("it should have added?\n");
                choice.addMember(member);
                return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            throw new Exception("Failed to add member: " + e.getMessage());
        }
    }
    
    public Choice addMemberApprove(Alternative alt, String memberName, Choice choice, LambdaLogger logger) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblAlt + " WHERE choiceId = ? AND description=?;");
            ps.setString(1, choice.choiceId);
            ps.setString(2, alt.description);
            ResultSet resultSet = ps.executeQuery();
            
            Member member = new Member(memberName);
            String memberId = (memberName + choice.choiceId);
            
            
            String altId = null;
            if (resultSet.next()) {
            	altId = resultSet.getString("altId");
                resultSet.close();
            }
            if(altId == null) {
            	return null;
            }

            
            logger.log("its trying to add to approvers table\n");
            ps = conn.prepareStatement("INSERT INTO " + tblApprovers + " (approveId, altId, memberId) values(?,?,?);");
            ps.setString(1,  memberName + altId);
            ps.setString(2,  altId);
            ps.setString(3,  memberId);
            ps.execute();
            logger.log("it should have added?\n");
            
            alt.addApprove(member);
            choice = getChoice(choice.choiceId, logger);
            
            return choice;
            
            
        } catch (Exception e) {
            throw new Exception("Failed to add member: " + e.getMessage());
        }
    }
    
    public Choice unselectMember(Alternative alt, String memberName, Choice choice, LambdaLogger logger) throws Exception {
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblAlt + " WHERE choiceId = ? AND description=?;");
            ps.setString(1, choice.choiceId);
            ps.setString(2, alt.description);
            ResultSet resultSet = ps.executeQuery();
            
            Member member = new Member(memberName);
            String memberId = (memberName + choice.choiceId);
            
            
            String altId = null;
            if (resultSet.next()) {
            	altId = resultSet.getString("altId");
                resultSet.close();
            }
            if(altId == null) {
            	return null;
            }

            // check both approvers and disapprover lists
            if (alt.approvers.contains(member)) {
            	logger.log("its trying to delete from approvers table\n");
                ps = conn.prepareStatement("DELETE FROM " + tblApprovers + " WHERE approveId=? AND altId=? AND memberId=?;");
                ps.setString(1,  memberName + altId);
                ps.setString(2,  altId);
                ps.setString(3,  memberId);
                ps.execute();
                logger.log("it should have deleted from approvers?\n");
                
                alt.removeApprove(member);
                choice = getChoice(choice.choiceId, logger);
                ps.close();
                
                return choice;
                
            } else if (alt.disapprovers.contains(member)) {
            	logger.log("its trying to delete from disapprovers table\n");
                ps = conn.prepareStatement("DELETE FROM " + tblDisapprovers + " WHERE approveId=? AND altId=? AND memberId=?;");
                ps.setString(1,  memberName + altId);
                ps.setString(2,  altId);
                ps.setString(3,  memberId);
                ps.execute();
                logger.log("it should have deleted from disapprovers?\n");
                
                alt.removeDisapprove(member);
                choice = getChoice(choice.choiceId, logger);
                ps.close();
                
                return choice;
                
            } else {
            	// not in either of the approver or disapprover lists, so we yeet
            	return null;
            }
            
            
        } catch (Exception e) {
            throw new Exception("Failed to remove member: " + e.getMessage());
        }
    }
    

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
    
    public Alternative getAlt(String choiceId, String desc) throws Exception {
        
        try {
            Alternative alt = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblAlt + " WHERE choiceId=? AND description=?;");
            ps.setString(1,  choiceId);
            ps.setString(2,  desc);
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
        choice.numMembers = resultSet.getInt("maxNumMembers");
        choice.isCompleted = resultSet.getBoolean("isCompleted");
        choice.dateCreated = resultSet.getDate("dateCreated");
        
        choice.altChosen = getAlt(resultSet.getString("chosenAlt"));
        
        choice.alternatives = getAltsFromChoice(choice.choiceId);
        choice.members = getMembersFromChoice(choice.choiceId);
        return choice;
    }
    
    private Alternative generateAlt(ResultSet resultSet) throws Exception {
    	//not actually the full alternative, as we don't need to get the approvals and feedback ect.
        Alternative alt = new Alternative();
        alt.description = resultSet.getString("description");
        
        String altId = resultSet.getString("altId");
        
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblApprovers + " WHERE altId=?;");
        ps.setString(1,  altId);
        ResultSet resultSet2 = ps.executeQuery();
        
        ArrayList<Member> members = new ArrayList<>();
        ArrayList<String> memberIds = new ArrayList<>();
        String currentMemberId = "";
        
        while(resultSet2.next()) {
        	currentMemberId = resultSet2.getString("memberId");
        	memberIds.add(currentMemberId);
        	
        	PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM " + tblMembers + " WHERE memberId=?;");
            ps2.setString(1, currentMemberId);
            ResultSet resultSet3 = ps2.executeQuery();
            if(resultSet3.next()) {
            	members.add(new Member(resultSet3.getString("name")));
            }
        }
        
        alt.approvers = members;
        
        
        
        //STILL NEED THE DISAPPROVERS AND STUFF LOL
        
        
        
        return alt;
    }
    
    private Member generateMember(ResultSet resultSet) throws Exception {
    	Member member = new Member();
        member.name = resultSet.getString("name");
        member.setPass(resultSet.getString("password"));
        
        return member;
    }
    
    public ArrayList<Alternative> getAltsFromChoice(String choiceId) throws Exception{
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