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
    	String choiceId = "bbc653d7-c75c-4810-9dcf-06144b507eb1";
    	String name = "Abby";
    	String altDesc = "country roads";
    	
    	UnselectChoiceRequest ccr = new UnselectChoiceRequest(choiceId, name, altDesc);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testUnselectDisapprove() {
    	String choiceId = "bbc653d7-c75c-4810-9dcf-06144b507eb1";
    	String name = "Vanessa";
    	String altDesc = "country roads";
    	
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
