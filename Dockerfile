FROM maven:3.5.4-jdk-8-alpine AS build
RUN mkdir -p /opt/app
COPY ./ /opt/app
RUN mvn -f /opt/app/pom.xml clean package

FROM openjdk:8u181-jdk-alpine3.8
LABEL app.name=dojodocker
RUN mkdir -p /opt/app
COPY --from=build /opt/app/target/dojodockerapp.jar /opt/app/dojodockerapp.jar
ARG DEFAULT_JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"
ENV JAVA_OPTS=$DEFAULT_JAVA_OPTS
ENTRYPOINT java $JAVA_OPTS  -jar /opt/app/dojodockerapp.jar
EXPOSE 8080