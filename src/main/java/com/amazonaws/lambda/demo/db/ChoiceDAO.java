
package com.amazonaws.lambda.demo.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.demo.model.Choice;
import com.amazonaws.lambda.demo.model.Member;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Constants" then it must be "Constants" in the SQL queries.
 * 
 * @author heineman
 *
 */
public class ChoiceDAO { 

	java.sql.Connection conn;
	
	final String tblName = "Constants";   // Exact capitalization

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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choiceId=?;");
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choiceId = ?;");
            ps.setString(1, choice.choiceId);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Choice c = generateChoice(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (choiceId,description,maxNumMembers,isCompleted,dateCompleted,chosenAlt) values(?,?,?,?,?,?);");
            ps.setString(1,  choice.choiceId);
            ps.setString(2,  choice.description);
            ps.setInt(3, choice.maxNumMembers);
            ps.setBoolean(4, choice.isCompleted);
            ps.setDate(5, choice.dateCompleted);
            ps.setString(6, null);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
    
    public boolean addMember(Choice choice, Member member) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE memberId = ?;");
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
            	ps = conn.prepareStatement("INSERT INTO " + tblName + " (memberId, choiceId, name, password) values(?,?);");
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
    
    public boolean updateChoice(Choice choice) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET value=? WHERE choiceId=?;";
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

    
    private Choice generateChoice(ResultSet resultSet) throws Exception {
        Choice choice = (Choice) resultSet.getObject("value");
        return choice;
    }

}