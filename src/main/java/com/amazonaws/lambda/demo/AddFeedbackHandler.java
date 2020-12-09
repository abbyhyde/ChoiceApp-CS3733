package com.amazonaws.lambda.demo;

import java.sql.Date;

import com.amazonaws.lambda.demo.db.ChoiceDAO;
import com.amazonaws.lambda.demo.http.AddFeedbackRequest;
import com.amazonaws.lambda.demo.http.AddFeedbackResponse;
import com.amazonaws.lambda.demo.http.ApproveChoiceRequest;
import com.amazonaws.lambda.demo.http.ApproveChoiceResponse;
import com.amazonaws.lambda.demo.http.ParticipateChoiceRequest;
import com.amazonaws.lambda.demo.http.ParticipateChoiceResponse;
import com.amazonaws.lambda.demo.model.Alternative;
import com.amazonaws.lambda.demo.model.Choice;
import com.amazonaws.lambda.demo.model.Feedback;
import com.amazonaws.lambda.demo.model.Member;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AddFeedbackHandler implements RequestHandler<AddFeedbackRequest,AddFeedbackResponse> {

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
	Choice addFeedback(String choiceId, String memberName, String altDesc, String feedbackDesc, Date date) throws Exception { 
		if (logger != null) { logger.log("in addFeedback\n"); }
		ChoiceDAO dao = new ChoiceDAO();
		
		// check if present
		Choice exist = dao.getChoice(choiceId, logger);
		logger.log(exist.description); //see if it actually got it
		
		if(exist.isCompleted) {
			return exist;
		}
		
		Alternative alt = dao.getAlt(choiceId, altDesc);
		logger.log(alt.description); //see if it actually got it
		
		Member member = new Member(memberName);
		Feedback feedback = new Feedback(member, feedbackDesc, date);
		
		if (exist != null && alt != null) {
			logger.log("about to add " + feedbackDesc); //see if it actually got it
			Choice updatedChoice = dao.addFeedback(alt, exist, feedback, logger);
			logger.log(feedbackDesc + "was added to the alternative");
			if (updatedChoice != null) {
				return updatedChoice;
			} else {
				System.out.println("Could not add to alternative");
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
	public AddFeedbackResponse handleRequest(AddFeedbackRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		AddFeedbackResponse response;
		
		
		try {
			Choice choice = addFeedback(req.choiceId, req.memberName, req.altDesc, req.feedbackDesc, req.date);
			if (choice != null) {
				response = new AddFeedbackResponse(choice);
			} else {
				response = new AddFeedbackResponse(choice, 422);
			}
		} catch (Exception e) {
			response = new AddFeedbackResponse(400);
		}

		return response;
	}
}
