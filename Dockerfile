
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ENTRYPOINT ["java","-jar","/app.jar"]