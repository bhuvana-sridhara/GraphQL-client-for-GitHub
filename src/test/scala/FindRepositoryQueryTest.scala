import builders.queryBuilders.RepositoryQueryBuilder
import builders.{First, Query}
import client.HttpClientBuilder.HttpComponents.HttpEmpty
import client.{HttpClient, HttpClientBuilder}
import com.typesafe.config.Config
import models.objects.{Repository, Search}
import org.scalatest.funsuite.AnyFunSuite
import utils.ConfigReader.getConfigDetails

class FindRepositoryQueryTest extends AnyFunSuite{

  val config:Config = getConfigDetails("application.conf")

  test("Successful"){
    //Build a HTTP client
    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]()
      .addBearerToken(config.getString("ACCESS_TOKEN"))
      .build

    //Build query
    val query:Query = new Query()
      .findRepository("incubator-mxnet",
        "apache",
        new RepositoryQueryBuilder()
          .includeUrl()
          .includeForks(
            new RepositoryQueryBuilder()
              .includeUrl()
              .includeIsFork()
              .includeName(),
            new First(10)
          )
      )

    val result  = httpObject.flatMap(_.executeQuery[Repository](query))

    assert(result.nonEmpty)
    //check with corresponding sample hit response
    assert(result.get.forks.nodes.head.getRepositoryName == "mxnet")
    assert(result.get.forks.nodes.head.getIsFork)
    assert(result.get.forks.nodes.head.getUrl.contains("https://github.com/"))
  }

  test("Unsuccessful query- Build Query in the proper Format"){
    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]()
      .addBearerToken(config.getString("ACCESS_TOKEN"))
      .build

    //Build query
    val query:Query = new Query()
      .findRepository("incubator-mxnet",
        "apache",
        new RepositoryQueryBuilder()
          .includeUrl()
          .includeForks(
            new RepositoryQueryBuilder(),
            new First(10)
          )
      )

    val result  = httpObject.flatMap(_.executeQuery[Repository](query))

    assert(result.isEmpty)
    assert(result.toString.contains("None"))

  }

  test("Unsuccessful query- Please provide Repository Type for Casting since the query created is of type Repository"){
    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]()
      .addBearerToken(config.getString("ACCESS_TOKEN"))
      .build

    //Build query
    val query:Query = new Query()
      .findRepository("incubator-mxnet",
        "apache",
        new RepositoryQueryBuilder()
          .includeUrl()
          .includeForks(
            new RepositoryQueryBuilder()
              .includeUrl()
              .includeIsFork()
              .includeName(),
            new First(10)
          )
      )

    val result  = httpObject.flatMap(_.executeQuery[Search](query))

    assert(result.isEmpty)
    assert(result.toString.contains("None"))

  }

}
