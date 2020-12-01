package com.amazonaws.lambda.demo;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.amazonaws.lambda.demo.http.GetChoicesAdminResponse;
import com.amazonaws.lambda.demo.http.UnselectChoiceRequest;
import com.amazonaws.lambda.demo.http.UnselectChoiceResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetChoicesAdminHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	GetChoicesAdminHandler handler = new GetChoicesAdminHandler();
    	GetChoicesAdminResponse resp = handler.handleRequest("", createContext("create"));
    	String responseString = new Gson().toJson(resp);
    	System.out.println(responseString);
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	GetChoicesAdminHandler handler = new GetChoicesAdminHandler();

    	GetChoicesAdminResponse resp = handler.handleRequest("", createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testGetAllChoices() {
        String SAMPLE_INPUT_STRING = "";
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    
   
}
