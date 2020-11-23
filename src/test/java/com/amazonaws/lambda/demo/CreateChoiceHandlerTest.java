package com.amazonaws.lambda.demo;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import com.amazonaws.lambda.demo.http.CreateChoiceRequest;
import com.amazonaws.lambda.demo.http.CreateChoiceResponse;
import com.amazonaws.lambda.demo.model.Alternative;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateChoiceHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	CreateChoiceHandler handler = new CreateChoiceHandler();
    	CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);
       
    	CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	CreateChoiceHandler handler = new CreateChoiceHandler();
    	CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

    	CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testShouldBeOk() {
    	int numMembers = 10;
    	String desc = "this is a choice";
    	Alternative alt1 = new Alternative("this is an alternative description");
    	ArrayList<Alternative> alts = new ArrayList<Alternative>();
    	alts.add(alt1);
    	
    	CreateChoiceRequest ccr = new CreateChoiceRequest(desc, alts, numMembers);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
        
        //DeleteChoiceRequest dcr = new DeleteChoiceRequest(var);
        //DeleteChoiceResponse d_resp = new DeleteChoiceHandler().handleRequest(dcr, createContext("delete"));
        //Assert.assertEquals(var, d_resp.name);
    }
    
    /*
    @Test
    public void testGarbageInput() {
    	String SAMPLE_INPUT_STRING = "{\"sdsd\": \"e3\", \"hgfgdfgdfg\": \"this is not a number\"}";
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 400);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    // overwrites into it
    @Test
    public void testCreateSystemConstant() {
    	// create constant
    	int rndNum = (int)(990*(Math.random()));
    	CreateChoiceRequest csr = new CreateChoiceRequest("to-delete-again" + rndNum, 9.29837, true);
        
    	CreateChoiceHandler createHandler = new CreateChoiceHandler();
    	CreateChoiceResponse resp = createHandler.handleRequest(csr, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
        
        // clean up 
        //createHandler.deleteSystemConstant("to-delete-again" + rndNum);
    }
    */
}
