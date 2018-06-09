FROM openjdk:8-jdk
MAINTAINER <dakishin@gmail.com>

# Set the working directory to /app
WORKDIR /app

EXPOSE 8080

COPY build/libs/*.jar /app/service.jar

CMD ["java", "-jar","-Dspring.profiles.active=dev", "/app/service.jar"]

