FROM amazoncorretto:17-alpine as build

ARG WEBHOOK_DOMAIN
ARG WEBHOOK_PATH
ARG TG_TOKEN

ENV WEBHOOK_DOMAIN=$WEBHOOK_DOMAIN
ENV WEBHOOK_PATH=$WEBHOOK_PATH
ENV TG_TOKEN=$TG_TOKEN

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