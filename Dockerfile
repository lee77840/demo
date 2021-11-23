# Docker file for java application
FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/*.jar
ENV JAVA_OPTS="-Dspring.profiles.active=LOCAL"
EXPOSE 8080
COPY build/libs/demo-*-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]
