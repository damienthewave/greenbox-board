#https://stackoverflow.com/questions/27767264/how-to-dockerize-maven-project-and-how-many-ways-to-accomplish-it
#FROM maven:3.6.3-openjdk-11 AS build
#COPY src /usr/src/app/src
#COPY pom.xml /usr/src/app
#RUN mvn -f /usr/src/app/pom.xml clean package
#FROM gcr.io/distroless/java
#COPY --from=build /usr/src/app/target/helloworld-1.0.0-SNAPSHOT.jar /usr/app/helloworld-1.0.0-SNAPSHOT.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/app/helloworld-1.0.0-SNAPSHOT.jar"]

FROM openjdk:11-jdk
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]