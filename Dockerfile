FROM amazoncorretto:17-alpine-jdk

COPY ./ /tmp

WORKDIR /tmp

EXPOSE 8080

RUN chmod +x ./gradlew

RUN ./gradlew build

ENTRYPOINT ["./gradlew", "bootRun"]


