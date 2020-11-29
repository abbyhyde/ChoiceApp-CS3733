package com.amazonaws.lambda.demo;

import com.amazonaws.lambda.demo.db.ChoiceDAO;
import com.amazonaws.lambda.demo.http.ApproveChoiceRequest;
import com.amazonaws.lambda.demo.http.ApproveChoiceResponse;
import com.amazonaws.lambda.demo.http.ParticipateChoiceRequest;
import com.amazonaws.lambda.demo.http.ParticipateChoiceResponse;
import com.amazonaws.lambda.demo.model.Alternative;
import com.amazonaws.lambda.demo.model.Choice;
import com.amazonaws.lambda.demo.model.Member;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ApproveChoiceHandler implements RequestHandler<ApproveChoiceRequest,ApproveChoiceResponse> {

	LambdaLogger logger;
	
	// To access S3 storage
	//private AmazonS3 s3 = null;
		
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	// public static final String REAL_BUCKET = "constants/";
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	Choice approveChoice(String choiceId, String memberName, String altDesc) throws Exception { 
		if (logger != null) { logger.log("in approveChoice\n"); }
		ChoiceDAO dao = new ChoiceDAO();
		
		// check if present
		Choice exist = dao.getChoice(choiceId, logger);
		logger.log(exist.description); //see if it actually got it
		
		Alternative alt = dao.getAlt(choiceId, altDesc);
		logger.log(alt.description); //see if it actually got it
		
		if (exist != null && alt != null) {
			logger.log("about to add " + memberName); //see if it actually got it
			Choice updatedChoice = dao.addMemberApprove(alt, memberName, exist, logger);
			logger.log(memberName + "was added to the approvers");
			if (updatedChoice != null) {
				return updatedChoice;
			} else {
				System.out.println("Could not add to choice");
				return null;
			}
			
		} else {
			return null;
		}
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	
	
	@Override 
	public ApproveChoiceResponse handleRequest(ApproveChoiceRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		ApproveChoiceResponse response;
		
		
		try {
			Choice choice = approveChoice(req.choiceId, req.memberName, req.altDesc);
			if (choice != null) {
				response = new ApproveChoiceResponse(choice);
			} else {
				response = new ApproveChoiceResponse(choice, 422);
			}
		} catch (Exception e) {
			response = new ApproveChoiceResponse(400);
		}

		return response;
	}
}