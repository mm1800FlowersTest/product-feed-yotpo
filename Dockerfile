FROM openjdk:8u151-jdk
COPY target/product-feed-yotpo-0.0.1-SNAPSHOT/ /product-feed-yotpo/
COPY product-feed-yotpo-0.0.1-SNAPSHOT.war /
#CMD ["java", "-cp", "/TestRabbitMQConsumer.jar:/lib/*", "com.fd.poc.rabbitmq.TestSubscriber"]
