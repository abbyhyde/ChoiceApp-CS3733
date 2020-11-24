package com.amazonaws.lambda.demo;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import com.amazonaws.lambda.demo.http.ParticipateChoiceRequest;
import com.amazonaws.lambda.demo.http.ParticipateChoiceResponse;
import com.amazonaws.lambda.demo.model.Alternative;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ParticipateChoiceHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	ParticipateChoiceHandler handler = new ParticipateChoiceHandler();
    	ParticipateChoiceRequest req = new Gson().fromJson(incoming, ParticipateChoiceRequest.class);
    	ParticipateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
    	String responseString = new Gson().toJson(resp);
    	System.out.println(responseString);
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	ParticipateChoiceHandler handler = new ParticipateChoiceHandler();
    	ParticipateChoiceRequest req = new Gson().fromJson(incoming, ParticipateChoiceRequest.class);

    	ParticipateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testAddMemberWithPassword() {
    	String choiceId = "47c435a0-1480-4d6a-a49e-04968c4f8d52";
    	String name = "abby";
    	//String password = "me12";
    	
    	ParticipateChoiceRequest ccr = new ParticipateChoiceRequest(choiceId, name);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    
    /*
    @Test
    public void testAddMemberWithoutPassword() {
    	String choiceId = "2234";
    	String name = "aislin";
    	
    	ParticipateChoiceRequest ccr = new ParticipateChoiceRequest(choiceId, name);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    
    @Test
    public void testExistingUser() {
    	String choiceId = "2234";
    	String name = "yeet";
    	String password = "sfdfsdfsd";
    	
    	ParticipateChoiceRequest ccr = new ParticipateChoiceRequest(choiceId, name, password);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    */
   
}
