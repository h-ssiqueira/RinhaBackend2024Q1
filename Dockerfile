FROM openjdk:21-jdk

ENV TIMEZONE=${timezone:-"America/Sao_Paulo"}

#WORKDIR /app
#COPY . /app

#RUN ["./mvnw", "-DskipTests", "package"]

#COPY /app/target/RinhaBackend2024Q1Application.jar /app/RinhaBackend2024Q1Application.jar

COPY /target/RinhaBackend2024Q1Application.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]