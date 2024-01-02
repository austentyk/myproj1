FROM openjdk:21-bookworm as builder

WORKDIR /src

COPY src src
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

RUN ./mvnw package -Dmvn.test.skip=true

WORKDIR /app

# copy and rename to app.jar
COPY --from=builder /src/target/myproj1-SNAPSHOT.jar app.jar

ENV PORT=8080

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar