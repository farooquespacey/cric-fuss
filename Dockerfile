FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/cric-fuss-1.0.jar /app/cric-fuss-1.0.jar

CMD ["java", "-jar", "cric-fuss-1.0.jar"]