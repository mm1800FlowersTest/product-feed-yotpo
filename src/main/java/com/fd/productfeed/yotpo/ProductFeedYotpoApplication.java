package com.fd.productfeed.yotpo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fd.productfeed.request.ProductsFeed;
import com.fd.productfeed.request.Product;
import com.fd.productfeed.controller.RestApiController;

import com.fd.productfeed.utils.FDJacksonUtils;

@SpringBootApplication
public class ProductFeedYotpoApplication {

	public static void main(String[] args) {
		
		//SpringApplication.run(ProductFeedYotpoApplication.class, args);
		
		System.out.println("Test run.");
		
		ProductsFeed productFeed = new ProductsFeed();
		Map<String, Product> products = new HashMap<String, Product>();
		
		productFeed.setUtoken("YOUR_UTOKEN");
        Product product1 = new Product();
        product1.setName("16 GB USB");
        product1.setUrl("www.somestore.com/product123456.html");
        products.put("gapi1", product1);
        Product product2 = new Product();
        product2.setName("USB Mouse");
        product2.setUrl("www.somestore.com/product123457.html");
        products.put("gapi2", product2);
        productFeed.setProducts(products);
        
        try {
			String productsFeedRequest = FDJacksonUtils.writeToStr(productFeed);
			
			System.out.println("requeset: ");
			System.out.println(productsFeedRequest);
			
			RestApiController prodFeed = new RestApiController();
			
			System.out.println("resp: " + prodFeed.createProductFeed(productFeed));
	        
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		//ApplicationContext context = SpringApplication.run(ProductFeedYotpoApplication.class, args);
		//System.out.println(( (RESTClientExample) context.getBean("restClient")).getAllEmployees());
	}
}