FROM gradle:8 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build
# Remove plain jar
RUN rm -rf build/libs/*-plain.jar

FROM eclipse-temurin:17 AS runtime

COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]