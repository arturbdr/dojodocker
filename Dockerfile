FROM arturbdr/custom-gatling:1.1 AS build
RUN mkdir -p /opt/app
COPY ./ /opt/app
RUN mvn -f /opt/app/pom.xml clean verify
RUN mvn -f /opt/app/pom.xml clean gatling:test -Dgatling.simulationClass=com.dojo.DojoDockerSimulation -DtestEnvironment=INGRESS -DrampUsers=20 -DrampOverSeconds=60 -DmaxDurationSeconds=60 -Dgatling.charting.indicators.lowerBound=20 -Dgatling.charting.indicators.higherBound=40

FROM openjdk:8u191-jdk-alpine3.9
LABEL app.name=dojodocker
RUN mkdir -p /opt/app
COPY --from=build /opt/app/target/dojodockerapp.jar /opt/app/dojodockerapp.jar
ARG DEFAULT_JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"
ENV JAVA_OPTS=$DEFAULT_JAVA_OPTS
ENTRYPOINT java $JAVA_OPTS  -jar /opt/app/dojodockerapp.jar
EXPOSE 8080