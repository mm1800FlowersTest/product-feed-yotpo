package com.fd.productfeed.yotpo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @author e019904
 */
//@SpringBootApplication
@RestController
public class ProductFeedYotpoController 
{
	@GetMapping("/ping")
	public String ping() 
	{
		return "Hello from Yotpo Product Feed";		
	}

	@GetMapping("/sendTestToRabbit")
	public String sendTestToRabbit() 
	{
		String routingKey = "test.topic.all";
		String message = "Hello World ";
		String result = "";

		ConnectionFactory factory = new ConnectionFactory();
		//factory.setHost("localhost");
		factory.setHost("rabbitmq.product-feed-rabbitmq.svc.cluster.local");
		Connection connection = null;
		Channel channel = null;
		try
		{
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare("testTopic", "topic");
			result = " [x] Sent '" + routingKey +"':'" + message + "'";
			System.out.println(result);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = "ERROR sending " + e.getMessage();
		} 
		finally
		{
			try
			{
				if(channel != null)
				{
					channel.close();
				}
				if(connection != null)
				{
					connection.close();
				}				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				result = result + " ALSO: " + e.getMessage();
			}
		}
		
		return result;		
	}	
}
