FROM openjdk:17-alpine
FROM maven:3.8.3-openjdk-17

EXPOSE 80

COPY ./ ./

RUN mvn clean package

CMD ["java", "-jar", "target/EdmondsKarpService-0.0.1-SNAPSHOT.jar"]