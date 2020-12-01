package com.amazonaws.lambda.demo;

import java.util.ArrayList;

import com.amazonaws.lambda.demo.db.ChoiceDAO;
import com.amazonaws.lambda.demo.http.ApproveChoiceRequest;
import com.amazonaws.lambda.demo.http.ApproveChoiceResponse;
import com.amazonaws.lambda.demo.http.GetChoicesAdminResponse;
import com.amazonaws.lambda.demo.http.ParticipateChoiceRequest;
import com.amazonaws.lambda.demo.http.ParticipateChoiceResponse;
import com.amazonaws.lambda.demo.http.UnselectChoiceRequest;
import com.amazonaws.lambda.demo.http.UnselectChoiceResponse;
import com.amazonaws.lambda.demo.model.Alternative;
import com.amazonaws.lambda.demo.model.Choice;
import com.amazonaws.lambda.demo.model.Member;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetChoicesAdminHandler implements RequestHandler<Object,GetChoicesAdminResponse> {

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
	ArrayList<Choice> getChoicesAdmin() throws Exception { 
		if (logger != null) { logger.log("in GetChoicesAdmin\n"); }
		ChoiceDAO dao = new ChoiceDAO();
		
		// check if present
		ArrayList<Choice> allChoices = dao.getAllChoices(logger);
		logger.log("" + allChoices.size()); //see if it actually got it
		
		return allChoices;
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	
	
	@Override 
	public GetChoicesAdminResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();

		GetChoicesAdminResponse response;
		
		
		try {
			ArrayList<Choice> choices = getChoicesAdmin();
			if (choices != null) {
				response = new GetChoicesAdminResponse(choices);
			} else {
				response = new GetChoicesAdminResponse(choices, 422);
			}
		} catch (Exception e) {
			response = new GetChoicesAdminResponse(400);
		}

		return response;
	}
}
