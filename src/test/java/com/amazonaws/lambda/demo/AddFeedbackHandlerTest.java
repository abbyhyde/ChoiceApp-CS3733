package com.amazonaws.lambda.demo;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.amazonaws.lambda.demo.http.AddFeedbackRequest;
import com.amazonaws.lambda.demo.http.AddFeedbackResponse;
import com.amazonaws.lambda.demo.model.Feedback;

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
    	String choiceId = "bbc653d7-c75c-4810-9dcf-06144b507eb1";
    	String name = "Vanessa";
    	String altDesc = "country roads";
    	String f = "banger";
    	
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
