FROM eclipse-temurin:17

RUN mkdir /app
COPY ./website-parser-api/target/website-parser-api-0.0.1-SNAPSHOT.jar /app/target/website-parser-api-0.0.1-SNAPSHOT.jar
WORKDIR /app
EXPOSE   8080

CMD ["java", "--enable-preview", "-jar", "/app/target/website-parser-api-0.0.1-SNAPSHOT.jar"]