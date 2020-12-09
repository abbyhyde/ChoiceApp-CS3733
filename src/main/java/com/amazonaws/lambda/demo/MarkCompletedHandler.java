package com.amazonaws.lambda.demo;

import java.sql.Date;

import com.amazonaws.lambda.demo.db.ChoiceDAO;
import com.amazonaws.lambda.demo.http.AddFeedbackRequest;
import com.amazonaws.lambda.demo.http.AddFeedbackResponse;
import com.amazonaws.lambda.demo.http.MarkCompletedRequest;
import com.amazonaws.lambda.demo.http.MarkCompletedResponse;
import com.amazonaws.lambda.demo.model.Alternative;
import com.amazonaws.lambda.demo.model.Choice;
import com.amazonaws.lambda.demo.model.Feedback;
import com.amazonaws.lambda.demo.model.Member;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MarkCompletedHandler implements RequestHandler<MarkCompletedRequest,MarkCompletedResponse> {

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
	Choice markCompleted(String choiceId, String altDesc) throws Exception { 
		if (logger != null) { logger.log("in markCompleted\n"); }
		ChoiceDAO dao = new ChoiceDAO();
		
		// check if present
		Choice exist = dao.getChoice(choiceId, logger);
		logger.log(exist.description); //see if it actually got it
		
		if(exist.isCompleted) {
			return exist;
		}
		
		Alternative alt = dao.getAlt(choiceId, altDesc);
		logger.log(alt.description); //see if it actually got it
		
		if (exist != null && alt != null) {
			logger.log("about to mark completed "); //see if it actually got it
			Choice updatedChoice = dao.markCompleted(exist, alt, logger);
			
			if (updatedChoice != null) {
				logger.log(updatedChoice.altChosen.description + "was marked completed");
				return updatedChoice;
			} else {
				System.out.println("Could not mark complete");
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
	public MarkCompletedResponse handleRequest(MarkCompletedRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		MarkCompletedResponse response;
		
		
		try {
			Choice choice = markCompleted(req.choiceId, req.altDesc);
			if (choice != null) {
				response = new MarkCompletedResponse(choice);
			} else {
				response = new MarkCompletedResponse(choice, 422);
			}
		} catch (Exception e) {
			response = new MarkCompletedResponse(400);
		}

		return response;
	}
}