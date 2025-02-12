FROM openjdk:21-bookworm
WORKDIR /app
COPY build/libs/*.jar /app/telegram-service.jar
EXPOSE 8888
ENV TZ=Europe/Minsk
CMD ["java", "-Duser.timezone=Europe/Minsk", "-jar", "telegram-service.jar"]