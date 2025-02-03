FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com

COPY target/receipt-processor-1.0.0.jar receipt-processor-1.0.0.jar
ENTRYPOINT ["java","-jar","/receipt-processor-1.0.0.jar"]

ENTRYPOINT ["java", "-jar", "receipt-processor.jar"]