FROM openjdk:21-jdk as BUILD

WORKDIR /app

COPY pom.xml /app
COPY src /app/src
COPY .mvn /app/.mvn
COPY mvnw /app/mvnw

RUN ./mvnw clean package -DskipTests
######################################
FROM openjdk:21-jdk as RUNTIME

WORKDIR /app

COPY --from=build /app/target/RinhaBackend2024Q1Application.jar /app/RinhaBackend2024Q1Application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseParallelGC", "-jar", "RinhaBackend2024Q1Application.jar"]
#
#"-XX:TieredStopAtLevel=1"
#"-noverify"
#"--spring.config.location=classpath:/application.properties"]
