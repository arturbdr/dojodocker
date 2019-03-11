FROM maven:3.6.0-jdk-11 AS build
RUN mkdir -p /opt/app
COPY ./ /opt/app
RUN mvn -f /opt/app/pom.xml clean verify

FROM openjdk:11.0.2-jdk
LABEL app.name=dojodocker
RUN mkdir -p /opt/app
COPY --from=build /opt/app/target/dojodockerapp.jar /opt/app/dojodockerapp.jar
ARG DEFAULT_JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"
ENV JAVA_OPTS=$DEFAULT_JAVA_OPTS
ENTRYPOINT java $JAVA_OPTS  -jar /opt/app/dojodockerapp.jar
EXPOSE 8080