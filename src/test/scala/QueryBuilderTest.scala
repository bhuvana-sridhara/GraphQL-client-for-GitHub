import builders._
import builders.queryBuilders.{IssueQueryBuilder, LanguageQueryBuilder, RepositoryOwnerQueryBuilder, RepositoryQueryBuilder, RepositoryTopicQueryBuilder, TopicQueryBuilder, UserQueryBuilder}
import org.scalatest.funsuite.AnyFunSuite

class QueryBuilderTest extends AnyFunSuite{

  /**
   *  Unit tests for all query builders
   */


  test("RepositoryQueryBuilder"){

    //Build query
    val query:RepositoryQueryBuilder =
        new RepositoryQueryBuilder()
          .includeUrl()
          .includeForks(
            new RepositoryQueryBuilder()
              .includeUrl()
              .includeIsFork()
              .includeName(),
            new First(10)
          )

    assert(query.scalars.head.contains("url"))
    assert(query.connections.head.scalars.contains("name"))
    assert(query.connections.head.scalars.contains("isFork"))
    assert(query.connections.head.scalars.contains("url"))
  }

  test("SearchQueryBuilder"){
    val query:SearchQueryBuilder =
        new SearchQueryBuilder(
          new First(10)
        )
          .includeRepository(new RepositoryQueryBuilder().includeName())
          .setLanguages("java, javascript")
          .setSearchTerms("ai, ml")
          .setSearchInContent(SearchQueryBuilder.NAME)
          .setNumberOfStars(new GreaterThan(5))
          .setNumberOfForks(new LesserThanEqualTo(4))


    assert(query.queryString.contains("language:java, javascript"))
    assert(query.queryString.contains("ai, ml"))
    assert(query.queryString.contains("stars:>5"))
    assert(query.queryString.contains("forks:<=4"))
  }

  test("IssueQueryBuilder"){
    val query:IssueQueryBuilder = new IssueQueryBuilder(

    )
      .includeRepository(new RepositoryQueryBuilder().includeName())
      .includeAuthor(new RepositoryOwnerQueryBuilder())
      .includeIsClosed()
      .includeTitle()
      .includeUrl()

    assert(query.scalars.contains("url"))
    assert(query.scalars.contains("title"))
    assert(query.scalars.contains("closed"))
  }

  test("LanguageQueryBuilder"){
    val query:LanguageQueryBuilder = new LanguageQueryBuilder()
        .includeColor()
        .includeName()

    assert(query.scalars.contains("name"))
    assert(query.scalars.contains("color"))
  }

  test("RepositoryOwnerQueryBuilder"){
    val query:RepositoryOwnerQueryBuilder = new RepositoryOwnerQueryBuilder()
        .includeUrl()
        .includeLogin()

    assert(query.scalars.contains("login"))
    assert(query.scalars.contains("url"))
  }

  test("RepositoryTopicQueryBuilder"){
    val query:RepositoryTopicQueryBuilder = new RepositoryTopicQueryBuilder()
      .includeTopic(new TopicQueryBuilder)


    assert(query.fields.head.isInstanceOf[TopicQueryBuilder])
  }

  test("UserQueryBuilder"){
    val query:UserQueryBuilder = new UserQueryBuilder()
      .includeFollowers(new UserQueryBuilder(),First(10))
      .includeFollowing(new UserQueryBuilder(),First(10))
      .includeRepositories(new RepositoryQueryBuilder(),First(10))
        .includeCompany()
        .includeCreatedAt()
        .includeEmail()
        .includeLogin()
        .includeName()
        .includeUrl()

    assert(query.scalars.contains("url"))
    assert(query.scalars.contains("name"))
    assert(query.scalars.contains("login"))
    assert(query.scalars.contains("email"))
    assert(query.scalars.contains("createdAt"))
    assert(query.scalars.contains("company"))
    assert(query.connections.head.isInstanceOf[RepositoryQueryBuilder])
    assert(query.connections.last.isInstanceOf[UserQueryBuilder])
  }

  test("TopicQueryBuilder"){
    val query:TopicQueryBuilder = new TopicQueryBuilder()
        .includeName()
        .includeStargazers(new UserQueryBuilder(),First(10))

    assert(query.scalars.contains("name"))
    assert(query.connections.head.isInstanceOf[UserQueryBuilder])
  }

}