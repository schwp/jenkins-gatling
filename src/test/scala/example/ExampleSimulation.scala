package example

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt

class ExampleSimulation extends Simulation {

  val addObj: ChainBuilder = exec(
    http("Add Object")
      .post("/objects")
      .body(
        RawFileBody("file.json")
      ).asJson
      .check(status.is(200))
  )

  val httpProtocol: HttpProtocolBuilder =
    http.baseUrl("https://api.restful-api.dev")

  val obj: ScenarioBuilder = scenario("ADD OBJECT").exec(addObj)

  setUp(
    obj.inject(constantConcurrentUsers(5).during(20.seconds))
  ).protocols(httpProtocol)
}
