import client.{HttpClient, HttpClientBuilder}
import client.HttpClientBuilder.HttpComponents.HttpEmpty
import org.scalatest.funsuite.AnyFunSuite

class HttpClientBuilderTest extends AnyFunSuite {

  test("Successful HTTP connection- valid token"){

    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]().addBearerToken("173f9a32dfa62e35664a4f662e519c78f4101295").build

    assert(httpObject.nonEmpty)

    assert(httpObject.get.headerPart.contains("Content-Type"))
    assert(httpObject.get.headerPart("Content-Type") == "application/json")

    assert(httpObject.get.headerPart.contains("Accept"))
    assert(httpObject.get.headerPart("Accept") == "application/json")

    assert(httpObject.get.headerPart.contains("Authorization"))
    assert(httpObject.get.headerPart("Authorization") == "Bearer 173f9a32dfa62e35664a4f662e519c78f4101295")

  }

  test("Unsuccessful HTTP connection- invalid token"){
    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]().addBearerToken("invalid").build

    assert(httpObject.isEmpty)
    assert(httpObject.toString.contains("None"))
  }

}
