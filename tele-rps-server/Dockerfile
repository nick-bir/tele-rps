FROM amazoncorretto:17-alpine as build

COPY gradlew .
COPY gradle gradle
COPY gradle.properties gradle.properties
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY src src

RUN ./gradlew build -x test

FROM amazoncorretto:17-alpine as api
ARG JAR_FILE=build/libs/app.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]