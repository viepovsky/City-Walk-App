FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY .mvn .mvn
COPY pom.xml .
COPY mvnw .
RUN ./mvnw dependency:resolve
COPY src src
RUN ./mvnw clean package
CMD ["java", "-jar", "/app/target/City-Walk-0.0.1-SNAPSHOT.jar"]