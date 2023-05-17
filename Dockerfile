FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY . .
RUN ./mvnw dependency:resolve
CMD ./mvnw spring-boot:run
