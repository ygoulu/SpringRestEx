# Preparation steps
FROM gradle:8.2.1-jdk17 AS BUILD_IMAGE
ENV APP_HOME=/root/dev/restex/
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME
# Copy all the files
COPY ./build.gradle ./gradlew ./gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
COPY ./src $APP_HOME/src/
# Build desirable JAR
RUN ./gradlew clean build -x test

# Run Jar
FROM openjdk:17-jdk-slim
WORKDIR /root/
COPY --from=BUILD_IMAGE '/root/dev/restex/build/libs/restex-0.0.1-SNAPSHOT.jar' '/app/restex.jar'
EXPOSE 8080
CMD ["java","-jar","/app/restex.jar"]