FROM openjdk:21-bookworm
WORKDIR /app
COPY build/libs/config-server-1.0.0.1.jar /app/config-server.jar
EXPOSE 8888
CMD ["java", "-jar", "config-server.jar"]