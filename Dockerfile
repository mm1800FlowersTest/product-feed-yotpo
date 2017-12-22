FROM openjdk:8u151-jdk
COPY target/product-feed-yotpo-0.0.1-SNAPSHOT.war /
CMD ["java", "-jar", "/product-feed-yotpo-0.0.1-SNAPSHOT.war"]
