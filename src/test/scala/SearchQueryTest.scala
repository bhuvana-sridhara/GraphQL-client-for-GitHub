import builders.queryBuilders.RepositoryQueryBuilder
import builders.{First, GreaterThan, LesserThanEqualTo, Query, SearchQueryBuilder}
import client.HttpClientBuilder.HttpComponents.HttpEmpty
import client.{HttpClient, HttpClientBuilder}
import models.objects.{Repository, Search}
import org.scalatest.funsuite.AnyFunSuite

class SearchQueryTest extends AnyFunSuite{

  test("Successful"){
    //Build a HTTP client
    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]()
      .addBearerToken("173f9a32dfa62e35664a4f662e519c78f4101295")
      .build

    //Build query
    val query:Query = new Query()
      .searchRepositories(
        new SearchQueryBuilder(
          new First(10)
        )
          .includeRepository(new RepositoryQueryBuilder().includeName())
          .setLanguages("java")
          .setSearchTerms("ai")
          .setSearchInContent(SearchQueryBuilder.NAME)
          .setNumberOfStars(new GreaterThan(5))
          .setNumberOfForks(new LesserThanEqualTo(4))
      )

    val result  = httpObject.flatMap(_.executeQuery[Search](query))

    //check with corresponding sample hit response
    assert(result.nonEmpty)
    assert(result.head.nodes.head.getRepositoryName.toLowerCase.contains("ai"))
  }

  test("Type error- Please provide Search Type for Casting since the query created is of type Search"){
    //Build a HTTP client
    val httpObject:Option[HttpClient] = new HttpClientBuilder[HttpEmpty]()
      .addBearerToken("173f9a32dfa62e35664a4f662e519c78f4101295")
      .build

    //Build query
    val query:Query = new Query()
      .searchRepositories(
        new SearchQueryBuilder(
          new First(10)
        )
      )

    val result  = httpObject.flatMap(_.executeQuery[Repository](query)) //THIS IS THE ERROR LINE
    //val result  = httpObject.flatMap(_.executeQuery[Search](query))

    assert(result.isEmpty)
    assert(result.toString.contains("None"))
  }

}