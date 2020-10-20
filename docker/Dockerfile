FROM openjdk:8-jdk-alpine

MAINTAINER "Picongzhi picongzhi@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/spring-boot-docker.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom", "-jar", "/app.jar"]