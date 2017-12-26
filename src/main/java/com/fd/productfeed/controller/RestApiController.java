package com.fd.productfeed.controller;

import java.io.IOException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fd.productfeed.request.ProductsFeed;
import com.fd.productfeed.utils.FDJacksonUtils;

@RestController
public class RestApiController {
	 
    @RequestMapping(method=RequestMethod.POST)
    public String createProductFeed(@RequestBody String productsFeed) {
    	String prodFeedString = productsFeed;
    	
    	/* Use if accepting ProductsFeed instead of String
		try {
			prodFeedString = FDJacksonUtils.writeToStr(productsFeed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
    	
        System.out.println("Creating feed : " + '\n' + prodFeedString);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        HttpEntity<?> httpEntity = new HttpEntity<Object>(prodFeedString, headers);

        RestTemplate restTemplate = new RestTemplate();

        String responsebody = null;
        
        try {
        ResponseEntity<String> response = restTemplate.exchange("https://api.yotpo.com/apps/YOUR_APP_KEY/products/mass_create", HttpMethod.POST, httpEntity, String.class);
        responsebody = response.getBody();
        } catch(HttpStatusCodeException e){
        	responsebody = e.getMessage();
        	} catch(RestClientException e){
        		e.printStackTrace();
        	}
        
        return responsebody;

    }	

}
