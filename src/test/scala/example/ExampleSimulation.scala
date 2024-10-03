package example

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.concurrent.ThreadLocalRandom

class ExampleSimulation extends Simulation {

  val checkPrice = exec(
    http("Get Price")
      .get("/v1/bpi/currentprice.json")
      .check(status.is(200))
  )

  val httpProtocol =
    http.baseUrl("https://api.coindesk.com")

  val bitcoin = scenario("Bitcoin price check").exec(checkPrice)

  setUp(
    bitcoin.inject(rampUsers(10).during(10))
  ).protocols(httpProtocol)
}
