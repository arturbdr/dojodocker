FROM openjdk:8u181-jdk-alpine3.8
LABEL app.name=dojodocker
RUN mkdir -p /opt/app
COPY target/dojodockerapp.jar /opt/app/dojodockerapp.jar
ARG DEFAULT_JAVA_OPTS="-Xms256m -Xmx512m"
ENV JAVA_OPTS=$DEFAULT_JAVA_OPTS
ENTRYPOINT java $JAVA_OPTS  -jar /opt/app/dojodockerapp.jar
EXPOSE 8080