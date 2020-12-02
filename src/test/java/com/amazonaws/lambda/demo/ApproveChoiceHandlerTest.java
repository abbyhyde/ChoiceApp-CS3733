package com.amazonaws.lambda.demo;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import com.amazonaws.lambda.demo.http.ApproveChoiceRequest;
import com.amazonaws.lambda.demo.http.ApproveChoiceResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ApproveChoiceHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	ApproveChoiceHandler handler = new ApproveChoiceHandler();
    	ApproveChoiceRequest req = new Gson().fromJson(incoming, ApproveChoiceRequest.class);
    	ApproveChoiceResponse resp = handler.handleRequest(req, createContext("create"));
    	String responseString = new Gson().toJson(resp);
    	System.out.println(responseString);
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	ApproveChoiceHandler handler = new ApproveChoiceHandler();
    	ApproveChoiceRequest req = new Gson().fromJson(incoming, ApproveChoiceRequest.class);

    	ApproveChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testAddApprove() {
    	String choiceId = "db53342a-0511-4a4f-a705-1d4ff745baa1";
    	String name = "Vanessa";
    	String altDesc = "Alt 1";
    	
    	ApproveChoiceRequest ccr = new ApproveChoiceRequest(choiceId, name, altDesc);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    
   
}
