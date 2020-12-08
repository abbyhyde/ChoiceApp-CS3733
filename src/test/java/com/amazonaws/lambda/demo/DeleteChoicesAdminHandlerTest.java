package com.amazonaws.lambda.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.http.DeleteChoicesAdminRequest;
import com.amazonaws.lambda.demo.http.DeleteChoicesAdminResponse;
import com.google.gson.Gson;

public class DeleteChoicesAdminHandlerTest extends LambdaTest {

	void testSuccessInput(String incoming) throws IOException {
		DeleteChoicesAdminHandler handler = new DeleteChoicesAdminHandler();
		DeleteChoicesAdminRequest req = new Gson().fromJson(incoming, DeleteChoicesAdminRequest.class);
		DeleteChoicesAdminResponse resp = handler.handleRequest(req, createContext("create"));
    	String responseString = new Gson().toJson(resp);
    	System.out.println(responseString);
        Assert.assertEquals(200, resp.httpCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testDeleteChoice() {
    	
    	DeleteChoicesAdminRequest ccr = new DeleteChoicesAdminRequest(9);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
        
        System.out.println(SAMPLE_INPUT_STRING);
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
       
    }
}
