FROM openjdk:21-bookworm AS builder

WORKDIR /src

COPY src src
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .


RUN ./mvnw package -Dmvn.test.skip=true
