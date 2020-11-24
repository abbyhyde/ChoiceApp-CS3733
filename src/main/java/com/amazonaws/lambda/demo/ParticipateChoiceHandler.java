package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.lambda.demo.db.ChoiceDAO;
import com.amazonaws.lambda.demo.http.ParticipateChoiceResponse;
import com.amazonaws.lambda.demo.http.ParticipateChoiceRequest;
import com.amazonaws.lambda.demo.model.Member;
import com.amazonaws.lambda.demo.model.Choice;

/**
 * Create a new constant and store in S3 bucket.

 * @author heineman
 */
public class ParticipateChoiceHandler implements RequestHandler<ParticipateChoiceRequest,ParticipateChoiceResponse> {

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
	Choice participateChoice(String choiceId, String memberName, String pass) throws Exception { 
		if (logger != null) { logger.log("in participateChoice\n"); }
		ChoiceDAO dao = new ChoiceDAO();
		
		// check if present
		Choice exist = dao.getChoice(choiceId, logger);
		logger.log(exist.description); //see if it actually got it
		
		Member member;
		if (pass != null) {
			member = new Member(memberName, pass);
		} else {
			member = new Member(memberName);
		}
		if (exist != null) {
			logger.log("about to add " + member.name); //see if it actually got it
			boolean added = dao.addMember(exist, member, logger);
			logger.log(member.name + "was added to the database");
			if (added) {
				return exist;
			} else {
				System.out.println("Could not add to choice, too many members.");
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
	public ParticipateChoiceResponse handleRequest(ParticipateChoiceRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		ParticipateChoiceResponse response;
		
		
		try {
			Choice choice = participateChoice(req.choiceId, req.memberName, req.pass);
			if (choice != null) {
				response = new ParticipateChoiceResponse(choice);
			} else {
				response = new ParticipateChoiceResponse(choice, 422);
			}
		} catch (Exception e) {
			response = new ParticipateChoiceResponse(400);
		}

		return response;
	}
}

