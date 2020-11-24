package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.ArrayList;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.gson.Gson;
import com.amazonaws.lambda.demo.db.ChoiceDAO;
import com.amazonaws.lambda.demo.model.Alternative;
import com.amazonaws.lambda.demo.http.CreateChoiceRequest;
import com.amazonaws.lambda.demo.http.CreateChoiceResponse;
import com.amazonaws.lambda.demo.model.Member;
import com.amazonaws.lambda.demo.model.Choice;

/**
 * Create a new constant and store in S3 bucket.

 * @author heineman
 */
public class CreateChoiceHandler implements RequestHandler<CreateChoiceRequest,CreateChoiceResponse> {

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
	boolean createChoice(String choiceId, String desc, ArrayList<Alternative> alts, int numM) throws Exception { 
		if (logger != null) { logger.log("in createChoice"); }
		ChoiceDAO dao = new ChoiceDAO();
		
		// check if present
		Choice exist = dao.getChoice(choiceId);
		String existString = new Gson().toJson(exist);
		logger.log(existString);
		
		Choice choice = new Choice(choiceId, desc, alts, numM);
		String choiceString = new Gson().toJson(choice);
		logger.log(choiceString);
		
		if (exist == null) {
			return dao.addChoice(choice);
		} else {
			return false;
		}
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	
	
	@Override 
	public CreateChoiceResponse handleRequest(CreateChoiceRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateChoiceResponse response;
		try {
			if (createChoice(req.choiceId, req.description, req.alternatives,req.numMembers)) {
				response = new CreateChoiceResponse(req.choiceId);
			} else {
				response = new CreateChoiceResponse(req.choiceId, 422);
			}
		} catch (Exception e) {
			response = new CreateChoiceResponse("Unable to create choice: " + req.choiceId + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}

