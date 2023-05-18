FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY .mvn .mvn
COPY pom.xml pom.xml
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src src
ENV TZ=Europe/Warsaw
CMD ./mvnw spring-boot:run
