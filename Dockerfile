FROM --platform=linux/arm64 eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN sed -i 's/\r$//' mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM --platform=linux/arm64 eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd --system spring && \
    useradd --system --gid spring --shell /bin/sh spring
COPY --from=builder /app/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring:spring
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
