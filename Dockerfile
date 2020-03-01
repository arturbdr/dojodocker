FROM maven:3.6.0-jdk-11 AS build
RUN groupadd -r usersapp && useradd -r -g usersapp appuser
RUN mkdir -p /opt/app
RUN mkdir -p /home/appuser/.m2/repository
RUN chown appuser /opt/app
RUN chown appuser /home/appuser/.m2/repository
USER appuser
COPY ./ /opt/app
RUN mvn -f /opt/app/pom.xml clean verify

FROM openjdk:11.0.2-jdk
LABEL app.name=dojodocker
RUN groupadd -r usersapp && useradd -r -g usersapp appuser
RUN mkdir -p /opt/app
RUN mkdir -p /home/appuser/.m2/repository
RUN chown appuser /opt/app
RUN chown appuser /home/appuser/.m2/repository
USER appuser
COPY --from=build /opt/app/target/dojodockerapp.jar /opt/app/dojodockerapp.jar
ARG DEFAULT_JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"
ENV JAVA_OPTS=$DEFAULT_JAVA_OPTS
ENTRYPOINT java $JAVA_OPTS  -jar /opt/app/dojodockerapp.jar
EXPOSE 8080