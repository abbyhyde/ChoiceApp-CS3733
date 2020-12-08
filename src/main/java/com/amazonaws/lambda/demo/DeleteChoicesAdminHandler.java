package com.amazonaws.lambda.demo;

import java.util.ArrayList;

import com.amazonaws.lambda.demo.db.ChoiceDAO;
import com.amazonaws.lambda.demo.http.DeleteChoicesAdminRequest;
import com.amazonaws.lambda.demo.http.DeleteChoicesAdminResponse;
import com.amazonaws.lambda.demo.http.GetChoicesAdminResponse;
import com.amazonaws.lambda.demo.model.Choice;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteChoicesAdminHandler implements RequestHandler<DeleteChoicesAdminRequest, DeleteChoicesAdminResponse>{

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
	ArrayList<Choice> deleteChoicesAdmin(long days) throws Exception { 
		if (logger != null) { logger.log("in DeleteChoicesAdmin\n"); }
		ChoiceDAO dao = new ChoiceDAO();
		
		// check if present
		ArrayList<Choice> allChoices = dao.deleteChoices(days, logger);
		logger.log("" + allChoices.size()); //see if it actually got it
		
		return allChoices;
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	
	
	@Override 
	public DeleteChoicesAdminResponse handleRequest(DeleteChoicesAdminRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		DeleteChoicesAdminResponse response;
		
		
		try {
			ArrayList<Choice> choices = deleteChoicesAdmin(req.days);
			if (choices != null) {
				response = new DeleteChoicesAdminResponse(choices);
			} else {
				response = new DeleteChoicesAdminResponse(choices, 422);
			}
		} catch (Exception e) {
			response = new DeleteChoicesAdminResponse(400);
		}

		return response;
	}
}
