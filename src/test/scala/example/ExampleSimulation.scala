package example

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt

class ExampleSimulation extends Simulation {

  val checkPrice: ChainBuilder = exec(
    http("Get Price")
      .get("/v1/bpi/currentprice.json")
      .check(status.is(200))
  )

  val httpProtocol: HttpProtocolBuilder =
    http.baseUrl("https://api.coindesk.com")

  val bitcoin: ScenarioBuilder = scenario("Bitcoin price check").exec(checkPrice)

  setUp(
    bitcoin.inject(rampUsers(10).during(2.minutes))
  ).protocols(httpProtocol)
}
