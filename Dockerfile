FROM openjdk:17-jdk-slim
WORKDIR /app/blob
COPY blob-0.0.1.jar /app/blob/app.jar
ENTRYPOINT ["java", "-jar", "/app/blob/app.jar"]
EXPOSE 9090