FROM openjdk:21-bookworm

WORKDIR /src

COPY src src
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .


RUN ./mvnw package -Dmvn.test.skip=true


COPY /src/target/myproj1-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar