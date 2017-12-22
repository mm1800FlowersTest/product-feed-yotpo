package com.fd.productfeed.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
@RequestMapping("/api")
public class RestApiController {
	
// -------------------Create mass product-------------------------------------------
	 
    @RequestMapping(value = "products/mass_create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProductFeed(@RequestBody ProductsFeed productsFeed) {
    	String prodFeedString = null;
		try {
			prodFeedString = FDJacksonUtils.writeToStr(productsFeed);
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Creating feed : " + prodFeedString);
 
       
        //MediaType mediaType = new MediaType("application", "json");
        //List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        //acceptableMediaTypes.add(mediaType);
        
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(mediaType);
        //headers.setAccept(acceptableMediaTypes);
        headers.add("Content-Type", "application/json");
        //headers.setConnection("https://api.yotpo.com/apps/YOUR_APP_KEY/products/mass_create");
        
       // ResponseEntity<String> response = restTemplate.postForEntity("https://api.yotpo.com/apps/YOUR_APP_KEY/products/mass_create", null, String.class
        
        HttpEntity<?> httpEntity = new HttpEntity<Object>(prodFeedString, headers);

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        
        try {
        	ResponseEntity<String> response = restTemplate.exchange("https://api.yotpo.com/apps/YOUR_APP_KEY/products/mass_create", HttpMethod.POST, httpEntity, String.class);	
        } catch(HttpStatusCodeException e) {
        	// trying to find response body:
        	String errorpayload = e.getResponseBodyAsString();
        	System.out.println("errorpayload: ");
        	System.out.println(e.getMessage());
        	System.out.println(e.getLocalizedMessage());
        	System.out.println(e.getRawStatusCode());
        	System.out.println(errorpayload);
        } catch(RestClientException e){
        	System.out.println("RestClientException: " + e.toString()); 
        }
        
        //HttpStatus status = response.getStatusCode();
        //String respBody = response.getBody();
        
        //System.out.println("resp: " + respBody);
        //System.out.println(response);
        
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }	

}
