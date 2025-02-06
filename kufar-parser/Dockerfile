FROM openjdk:21-bookworm
WORKDIR /app
COPY build/libs/*.jar /app/kufar-parser.jar
EXPOSE 8888
ENV TZ=Europe/Minsk
CMD ["java", "-Duser.timezone=Europe/Minsk", "-jar", "kufar-parser.jar"]