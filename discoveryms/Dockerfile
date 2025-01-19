FROM openjdk:21-bookworm
WORKDIR /app
COPY build/libs/discovery-ms-1.0.0.1.jar /app/discovery-server.jar
EXPOSE 8761
CMD ["java", "-jar", "discovery-server.jar"]