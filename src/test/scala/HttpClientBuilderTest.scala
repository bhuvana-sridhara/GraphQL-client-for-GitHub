import client.{HttpClient, HttpClientBuilder}
import client.HttpClientBuilder.HttpComponents.HttpEmpty
import com.typesafe.config.Config
import utils.ConfigReader.getConfigDetails
import org.scalatest.funsuite.AnyFunSuite

class HttpClientBuilderTest extends AnyFunSuite {

  val config:Config = getConfigDetails("application.conf")

  test("Successful HTTP connection- valid token"){

    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]().addBearerToken(config.getString("ACCESS_TOKEN")).build

    assert(httpObject.nonEmpty)

    assert(httpObject.get.headerPart.contains("Content-Type"))
    assert(httpObject.get.headerPart("Content-Type") == "application/json")

    assert(httpObject.get.headerPart.contains("Accept"))
    assert(httpObject.get.headerPart("Accept") == "application/json")

    assert(httpObject.get.headerPart.contains("Authorization"))
    assert(httpObject.get.headerPart("Authorization") == "Bearer "+config.getString("ACCESS_TOKEN"))

  }

  test("Unsuccessful HTTP connection- invalid token"){
    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]().addBearerToken("invalid").build

    assert(httpObject.isEmpty)
    assert(httpObject.toString.contains("None"))
  }

}
