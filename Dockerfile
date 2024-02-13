#FROM maven:3.9.6 AS build
FROM openjdk:21-jdk
ENV TIMEZONE=${timezone:-"America/Sao_Paulo"}

#WORKDIR /app

#COPY pom.xml /app
#COPY src /app/src
#COPY .mvn /app/.mvn
#COPY mvnw /app/mvnw

#RUN ./mvnw clean package -DskipTests



#WORKDIR /app

#COPY --from=build /app/target/RinhaBackend2024Q1Application.jar /app/RinhaBackend2024Q1Application.jar
COPY /target/RinhaBackend2024Q1Application.jar RinhaBackend2024Q1Application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseParallelGC", "-XX:TieredStopAtLevel=1", "-noverify", "-Xmx128m", "-jar", "RinhaBackend2024Q1Application.jar", "--spring.config.location=classpath:/application.properties"]
