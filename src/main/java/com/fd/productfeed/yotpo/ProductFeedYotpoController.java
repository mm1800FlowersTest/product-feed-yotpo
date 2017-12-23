package com.fd.productfeed.yotpo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author e019904
 */
//@SpringBootApplication
@RestController
public class ProductFeedYotpoController 
{
	@GetMapping("/ping")
	public String getDeliveryCalendarHello() 
	{
		return "Hello from Yotpo Product Feed";		
	}

}
