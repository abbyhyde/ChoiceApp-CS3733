package com.amazonaws.lambda.demo;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import com.amazonaws.lambda.demo.http.UnselectChoiceRequest;
import com.amazonaws.lambda.demo.http.UnselectChoiceResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UnselectChoiceHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	UnselectChoiceHandler handler = new UnselectChoiceHandler();
    	UnselectChoiceRequest req = new Gson().fromJson(incoming, UnselectChoiceRequest.class);
    	UnselectChoiceResponse resp = handler.handleRequest(req, createContext("create"));
    	String responseString = new Gson().toJson(resp);
    	System.out.println(responseString);
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	UnselectChoiceHandler handler = new UnselectChoiceHandler();
    	UnselectChoiceRequest req = new Gson().fromJson(incoming, UnselectChoiceRequest.class);

    	UnselectChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testUnselectApprove() {
    	String choiceId = "013788ea-614e-4628-ab83-4e0524084392";
    	String name = "Zach";
    	String altDesc = "gsal";
    	
    	UnselectChoiceRequest ccr = new UnselectChoiceRequest(choiceId, name, altDesc);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    
   
}
