/*
 * 
 */
package com.fd.productfeed.yotpo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fd.productfeed.controller.RestApiController;
import com.fd.productfeed.request.Product;
import com.fd.productfeed.request.ProductsFeed;
import com.fd.productfeed.utils.FDJacksonUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Product feed is currently set up in main. Two products hardcoded for demo.
 * RestApiController currently sysouts response status code (401 without APP_KEY):
 * 		- 12/22/17 commented out for rabbit testing
 * 
 * @author csears
 */
@SpringBootApplication
public class ProductFeedYotpoApplication 
{
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		SpringApplication.run(ProductFeedYotpoApplication.class, args);	
        
        try 
        {
			testRabbit();
		} 
        catch (IOException e) 
        {
			System.out.println("ioexception: " + e.toString());
			e.printStackTrace();
		} 
        catch (TimeoutException e) 
        {
			System.out.println("timeoutexception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public static void testRabbit() throws IOException, TimeoutException 
	{
		// Referenced code from TestRabbitMQConsumer
		// Accepts incoming from TestRabbitMQproducer
		
		String EXCHANGE_NAME = "testTopic";
		
		ConnectionFactory factory = new ConnectionFactory();
		//factory.setHost("localhost");
		factory.setHost("rabbitmq.product-feed-rabbitmq.svc.cluster.local");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();
		String bindingKey = "test.*.all";
		channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
				
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
			{
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
				
				// Assume 'message' will be JSON body - send to Yotpo.
				String demoMessage = createDemoFeed();
				RestApiController prodFeed = new RestApiController();
				String response = prodFeed.createProductFeed(demoMessage);	// send JSON to Yotpo. Replace 'demoFeed' with 'message'
				System.out.println("Response from Yotpo: " + '\n' + response);
			}
		};

		channel.basicConsume(queueName, true, consumer);		
	}
	
	public static String createDemoFeed() {
		// mock JSON received from rabbit producer:
		
		String prodFeed = "{\"utoken\" : \"YOUR_UTOKEN\", " +
				"\"products\" : { " +
				"\"gapi2\" : {" +
				"\"name\" : \"USB Mouse\"," +
				"\"url\" : \"www.somestore.com/product123457.html\"" +
				"}," +
				"\"gapi1\" : {" +
				"\"name\" : \"16 GB USB\"," +
			    "\"url\" : \"www.somestore.com/product123456.html\"" +
				"}" +
			    "}"+
				"}"
				;
		
		return prodFeed;
	}
	
	public static String setProducts() {
		
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
        
        String productsFeedRequest = null; 
        
        try {
			productsFeedRequest = FDJacksonUtils.writeToStr(productFeed);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return productsFeedRequest;
	}
}
