package com.amazonaws.lambda.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.http.MarkCompletedRequest;
import com.amazonaws.lambda.demo.http.MarkCompletedResponse;
import com.google.gson.Gson;

public class MarkCompletedHandlerTest extends LambdaTest {
 	
	void testSuccessInput(String incoming) throws IOException {
 		MarkCompletedHandler handler = new MarkCompletedHandler();
 		MarkCompletedRequest req = new Gson().fromJson(incoming, MarkCompletedRequest.class);
 		MarkCompletedResponse resp = handler.handleRequest(req, createContext("create"));
    	String responseString = new Gson().toJson(resp);
    	System.out.println(responseString);
        Assert.assertEquals(200, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testMarkCompleted() {
    	String choiceId = "f8733481-11c9-44ff-a096-8acc7ef5296c";
    	String altDesc = "stuffing (if y'all don't pick this you're a monster)";
    	
    	MarkCompletedRequest ccr = new MarkCompletedRequest(choiceId, altDesc);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
}
