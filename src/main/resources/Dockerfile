FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-alpine as prod
RUN mkdir /app
COPY --from=builder /app/target/*.jar /app/app.jar
COPY src/main/resources/application.yaml /app/application.yaml
COPY src/main/resources/data.sql /app/data.sql

ENV SERVER_PORT=8080
ENV DATABASE_URL=$DATABASE_URL
ENV DATABASE_USERNAME=$DATABASE_USERNAME
ENV DATABASE_PASSWORD=$DATABASE_PASSWORD
ENV SAL=$SAL
ENV SIGNATURE_KEY=$SIGNATURE_KEY

WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar", "--spring.config.location=/app/application.yaml", "--spring.sql.init.data-locations=file:/app/data.sql"]