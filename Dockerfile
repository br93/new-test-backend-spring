FROM eclipse-temurin:17-jre-alpine
COPY target/test-backend-0.0.1-SNAPSHOT.jar testbackend.jar
CMD ["java", "-jar", "testbackend.jar"]