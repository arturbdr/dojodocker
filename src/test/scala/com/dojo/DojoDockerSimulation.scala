package com.dojo

import io.gatling.core.Predef._
import io.gatling.core.feeder.SourceFeederBuilder
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.HeaderValues.ApplicationJson
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder

import scala.concurrent.duration._

/**
  * Running this stress test :
  *
  * LOCAL
  * mvn clean gatling:test -Dgatling.simulationClass=com.dojo.DojoDockerSimulation -DtestEnvironment=LOCAL
  * mvn clean gatling:test -Dgatling.simulationClass=com.dojo.DojoDockerSimulation -DtestEnvironment=INGRESS
  *
  *
  */
class DojoDockerSimulation extends Simulation {
  println("===> DojoDockerSimulation <===")

  val environments = Map(
    "LOCAL" -> "http://localhost:8080",
    "INGRESS" -> "http://192.168.99.107/ingress/")

  val environment: String = Option(System.getProperty("testEnvironment")) getOrElse "LOCAL"
  val baseURL: String = Option(System.getProperty("baseURL")) getOrElse environments(environment)

  val jsonFileFeeder: SourceFeederBuilder[Any] = jsonFile("gatling-user-feed.json").circular

  val httpConf: HttpProtocolBuilder = http
    .baseUrl(baseURL)
    .disableCaching
    .acceptHeader(ApplicationJson)
    .userAgentHeader("5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")

  val reqDojodockerSlow: HttpRequestBuilder = http("/dojodocker-slow")
    .get("/dojodocker-slow")
    .queryParam("name", "${name}")
    .queryParam("age", "${age}")
    .check(status.is(200))


  var scnReqDojodockerslow: ScenarioBuilder = scenario("Get user with random delay time")
    .feed(jsonFileFeeder)
    .exec(reqDojodockerSlow)
  //    .exec { session =>
  //      println(session)
  //      session
  //    }


  setUp(
    scnReqDojodockerslow.inject(
      incrementUsersPerSec(1)
        .times(5)
        .eachLevelLasting(10 seconds)
        .startingFrom(1)
    ))
    .maxDuration(30 seconds)
    .protocols(httpConf)
}