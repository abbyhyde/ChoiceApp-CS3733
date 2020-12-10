package com.amazonaws.lambda.demo;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import com.amazonaws.lambda.demo.http.DisapproveChoiceRequest;
import com.amazonaws.lambda.demo.http.DisapproveChoiceResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DisapproveChoiceHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	DisapproveChoiceHandler handler = new DisapproveChoiceHandler();
    	DisapproveChoiceRequest req = new Gson().fromJson(incoming, DisapproveChoiceRequest.class);
    	DisapproveChoiceResponse resp = handler.handleRequest(req, createContext("create"));
    	String responseString = new Gson().toJson(resp);
    	System.out.println(responseString);
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	DisapproveChoiceHandler handler = new DisapproveChoiceHandler();
    	DisapproveChoiceRequest req = new Gson().fromJson(incoming, DisapproveChoiceRequest.class);

    	DisapproveChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testAddDisapprove() {
    	String choiceId = "5caf6179-9c89-4cb4-b499-a229487489fb";
    	String name = "Vanessa";
    	String altDesc = "this is an alternative description";
    	
    	DisapproveChoiceRequest ccr = new DisapproveChoiceRequest(choiceId, name, altDesc);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    
   
}
