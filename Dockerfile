FROM gradle:8 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build && rm -rf build/libs/*-plain.jar

FROM eclipse-temurin:17 AS runtime

RUN useradd -m app
USER app

COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]