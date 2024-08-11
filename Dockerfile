FROM alpine:latest AS base

RUN apk add git openjdk21

COPY . .

RUN ./gradlew bootJar

FROM alpine:latest AS result

RUN apk add openjdk21

WORKDIR /app
COPY --from=base /build/libs/user-service-*.jar ./user-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "user-service.jar"]