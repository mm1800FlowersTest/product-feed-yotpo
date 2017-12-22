FROM openjdk:8u151-jdk
COPY lib/ /lib/
COPY product-feed-yotpo.jar /
CMD ["java", "-cp", "/product-feed-yotpo.jar:/lib/*", "com.fd.productfeed.yotpo.ProductFeedYotpoApplication"]