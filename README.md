# Rinha Backend 2024 Q1

![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Java](https://img.shields.io/badge/java%2021-000000?style=for-the-badge&logo=openjdk&logoColor=white)
![NGINX](https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white)
![Postgresql](https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)

![Maven](https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

![Liquibase](https://img.shields.io/badge/liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)

![Junit](https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Swagger](https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

## Sumário
- [Execução](#execução)
- [Documentação](#documentação)
- [Otimizações](#otimizações)
  - [Dockerfile](#dockerfile)

## Execução

```bash
docker compose up --build
```

## Documentação
![ER](docs/ER.png)

[Swagger](docs/swagger.yml)

## [Otimizações](https://www.baeldung.com/spring-boot-startup-speed)

### Dockerfile
`"-XX:+UseParallelGC", "-XX:TieredStopAtLevel=1"`

### Spring Boot
Exclude autoConfiguration classes

