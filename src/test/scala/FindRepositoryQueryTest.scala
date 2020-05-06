import builders.queryBuilders.RepositoryQueryBuilder
import builders.{First, Query}
import client.HttpClientBuilder.HttpComponents.HttpEmpty
import client.{HttpClient, HttpClientBuilder}
import models.objects.Repository
import org.scalatest.funsuite.AnyFunSuite

class FindRepositoryQueryTest extends AnyFunSuite{
  test("Successful"){
    //Build a HTTP client
    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]()
      .addBearerToken("173f9a32dfa62e35664a4f662e519c78f4101295")
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
      .addBearerToken("173f9a32dfa62e35664a4f662e519c78f4101295")
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

}
