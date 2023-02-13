FROM openjdk:17-alpine

EXPOSE 80

COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]