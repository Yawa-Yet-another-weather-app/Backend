FROM eclipse-temurin:21
COPY ./target/yawa-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]