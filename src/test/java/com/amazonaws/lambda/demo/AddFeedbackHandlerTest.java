package com.amazonaws.lambda.demo;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.amazonaws.lambda.demo.http.AddFeedbackRequest;
import com.amazonaws.lambda.demo.http.AddFeedbackResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddFeedbackHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	AddFeedbackHandler handler = new AddFeedbackHandler();
    	AddFeedbackRequest req = new Gson().fromJson(incoming, AddFeedbackRequest.class);
    	AddFeedbackResponse resp = handler.handleRequest(req, createContext("create"));
    	String responseString = new Gson().toJson(resp);
    	System.out.println(responseString);
        Assert.assertEquals(200, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testAddFeedback() {
    	String choiceId = "db53342a-0511-4a4f-a705-1d4ff745baa1";
    	String name = "Vanessa";
    	String altDesc = "Alt 1";
    	String f = "testing feedback again";
    	
    	AddFeedbackRequest ccr = new AddFeedbackRequest(choiceId, altDesc, name, f);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
    
   
}
