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
		
		if(exist.containsMember(member) && exist.isCompleted) {
			return exist;
		}
		if(exist.isCompleted && !(exist.containsMember(member))) {
			Choice c = new Choice();
			c.choiceId = "433";
			return c;
		}

		if (exist != null) {
			logger.log("about to add " + member.name); //see if it actually got it
			String message = dao.addMember(exist, member, logger);
			logger.log(member.name + "was added to the database");
			if (message.equals("200")) {
				return exist;
			} else if (message.equals("420")){
				Choice c = new Choice();
				c.choiceId = "420";
				return c;
			} else if (message.equals("444")){
				Choice c = new Choice();
				c.choiceId = "444";
				return c;
			}
			
		} 
		return null;
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
				if (choice.choiceId.equals("433")) { //choice is already completed
					response = new ParticipateChoiceResponse(choice, 433);
				}
				else if (choice.choiceId.equals("420")) { //too many members
					response = new ParticipateChoiceResponse(choice, 420);
				}
				else if (choice.choiceId.equals("444")) { //password doesnt match
					response = new ParticipateChoiceResponse(choice, 444);
				}
				else {
					response = new ParticipateChoiceResponse(choice, 200);
				}
			} 
			else {
				response = new ParticipateChoiceResponse(400);
			}
		} catch (Exception e) {
			response = new ParticipateChoiceResponse(400);
		}

		return response;
	}
}

