package io.gatling.demo;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class FoundationSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://testgatling-develop.scfhq-publiccrossdev01-391a523e0203d3683790f242c9079785-0000.eu-de.containers.appdomain.cloud")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("application/json, text/plain, */*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:100.0) Gecko/20100101 Firefox/100.0");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "none"),
    Map.entry("Sec-Fetch-User", "?1"),
    Map.entry("Upgrade-Insecure-Requests", "1")
  );
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin")
  );
  
  private Map<CharSequence, String> headers_3 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Origin", "https://testgatling-develop.scfhq-publiccrossdev01-391a523e0203d3683790f242c9079785-0000.eu-de.containers.appdomain.cloud"),
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin")
  );
  
  private Map<CharSequence, String> headers_4 = Map.ofEntries(
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin")
    //Map.entry("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBsb2NhbGhvc3QuZXMiLCJhdXRoIjoiUk9MRV9BRE1JTixST0xFX1VTRVIiLCJleHAiOjE2NTQxNjEwNjF9.3g3imDzRkZwccqWvzXhnzVzBMgR94WzfVGlS8gwE1Mz746wEcs2-qCk6GL7rgApq9ZAdM174UBFXM-qDIgsFkA")
  );
  
  private Map<CharSequence, String> headers_13 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Origin", "https://testgatling-develop.scfhq-publiccrossdev01-391a523e0203d3683790f242c9079785-0000.eu-de.containers.appdomain.cloud"),
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin")
    //Map.entry("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBsb2NhbGhvc3QuZXMiLCJhdXRoIjoiUk9MRV9BRE1JTixST0xFX1VTRVIiLCJleHAiOjE2NTQxNjEwNjF9.3g3imDzRkZwccqWvzXhnzVzBMgR94WzfVGlS8gwE1Mz746wEcs2-qCk6GL7rgApq9ZAdM174UBFXM-qDIgsFkA")
  );


  private ScenarioBuilder scn = scenario("FoundationSimulation")
    .exec(
      http("index")
        .get("/")
        .headers(headers_0)
        .check(status().is(200))
    )
    .pause(9)
    .exec(
      http("login page")
        .get("/login?page=1&sort=id,asc")
        .headers(headers_0)
        .check(bodyBytes().is(RawFileBody("io/gatling/demo/foundationsimulation/0000_response.html")))
        .resources(
          http("api account")
            .get("/api/account")
            .headers(headers_1)
            .check(status().is(401))
            
        )
    )
    .pause(9)
    .exec(
      http("autentication")
        .post("/api/authenticate")
        .headers(headers_3)
        .body(RawFileBody("io/gatling/demo/foundationsimulation/0003_request.json"))
        .check(jsonPath("$.id_token").saveAs("id_token"))
        .check(status().is(200))
        .resources(
          http("api account autenticated")
            .get("/api/account")
            .headers(headers_4)
            .header("authorization","Bearer ${id_token}")
            .check(status().is(200))
        )
    )
    .pause(6)
    .exec(
      http("users page")
        .get("/api/admin/users?page=0&size=20&sort=id,asc")
        .headers(headers_4)
        .header("authorization","Bearer ${id_token}")
        .check(status().is(200))
    )
    .pause(2)
    .exec(
      http("threaddump page")
        .get("/management/threaddump")
        .headers(headers_4)
        .header("authorization","Bearer ${id_token}")
        .check(status().is(404))
        
        .resources(
          http("jhimetrics page")
            .get("/management/jhimetrics")
            .headers(headers_4)
            .header("authorization","Bearer ${id_token}")
            .check(status().is(404))
            
        )
    )
    .pause(2)
    .exec(
      http("management health")
        .get("/management/health")
        .headers(headers_4)
        .header("authorization","Bearer ${id_token}")
        .check(status().is(404))
        
    )
    .pause(2)
    .exec(
      http("configprops page")
        .get("/management/configprops")
        .headers(headers_4)
        .header("authorization","Bearer ${id_token}")
        .check(status().is(404))
        
        .resources(
          http("env page")
            .get("/management/env")
            .headers(headers_4)
            .header("authorization","Bearer ${id_token}")
            .check(status().is(404))
            
        )
    )
    .pause(2)
    .exec(
      http("loggers page")
        .get("/management/loggers")
        .headers(headers_4)
        .header("authorization","Bearer ${id_token}")
        .check(status().is(404))
        
    )
    .pause(2)
    .exec(
      http("account page")
        .get("/api/account")
        .headers(headers_4)
        .header("authorization","Bearer ${id_token}")
        
    )
    .pause(6)
    .exec(
      http("account change")
        .post("/api/account")
        .headers(headers_13)
        .header("authorization","Bearer ${id_token}")
        .body(RawFileBody("io/gatling/demo/foundationsimulation/0013_request.bin"))
        .resources(
          http("account changed page")
            .get("/api/account")
            .headers(headers_4)
            .header("authorization","Bearer ${id_token}")
            .check(status().is(200))
        )
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
