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
  * mvn clean gatling:test -Dgatling.simulationClass=com.dojo.DojoDockerSimulation -DtestEnvironment=LOCAL -DrampUsers=20 -DrampOverSeconds=60 -DmaxDurationSeconds=60 -Dgatling.charting.indicators.lowerBound=20 -Dgatling.charting.indicators.higherBound=40
  * mvn clean gatling:test -Dgatling.simulationClass=com.dojo.DojoDockerSimulation -DtestEnvironment=INGRESS -DrampUsers=20 -DrampOverSeconds=60 -DmaxDurationSeconds=60 -Dgatling.charting.indicators.lowerBound=20 -Dgatling.charting.indicators.higherBound=40
  *
  *
  */
class DojoDockerSimulation extends Simulation {
  println("===> DojoDockerSimulation <===")

  val environments = Map(
    "LOCAL" -> "http://localhost:8080",
    "INGRESS" -> "https://192.168.99.100/ingress")

  val environment: String = Option(System.getProperty("testEnvironment")) getOrElse "LOCAL"
  val baseURL: String = Option(System.getProperty("baseURL")) getOrElse environments(environment)
  val rampUsersEnv: String = Option(System.getProperty("rampUsers")) getOrElse "5"
  val rampOverEnv: String = Option(System.getProperty("rampOverSeconds")) getOrElse "30"
  val maxDurationSeconds: String = Option(System.getProperty("maxDurationSeconds")) getOrElse "30"

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
      rampUsersPerSec(1) to rampUsersEnv.toInt during (rampOverEnv.toInt seconds)
    ))
    .assertions(global.responseTime.max.lt(300))
    .maxDuration(maxDurationSeconds.toInt seconds)
    .protocols(httpConf)
}