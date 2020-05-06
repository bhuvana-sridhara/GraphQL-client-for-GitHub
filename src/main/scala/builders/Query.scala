package builders

import builders.queryBuilders.RepositoryQueryBuilder

/**
 * Returns a query object which has a query string that can be passed to
 * the GraphQL API. Contains functions to construct two different kinds of
 * queries
 *
 * @param queryString The query string to be sent to GraphQL excluding the
 *                    outer "query" tag
 */
case class Query(queryString: String = "") {

  // contains a string describing the return expected by the underlying query
  var returnType: String = ""

  // getter for query string
  def getQueryString: String = this.queryString

  // getter for returnString
  def getReturnType: String = this.returnType

  /**
   * Returns a [[Query]] object for querying a single repository. Query syntax given
   * in [[https://developer.github.com/v4/query/#fields]]
   *
   * @param name name of the repository to search for
   * @param owner owner of the repository to search for
   * @param repositoryQueryBuilder [[RepositoryQueryBuilder]] object that contains
   *                              functions to specify the sub-fields of the query
   * @return object of type [[Query]]
   */
  def findRepository(
                      name: String,
                      owner: String,
                      repositoryQueryBuilder: RepositoryQueryBuilder
                    ): Query = {

    // modify top query to include the required arguments
    repositoryQueryBuilder.topQuery = repositoryQueryBuilder.topQuery +
      s"""(name:\"$name\", owner:\"$owner\")"""

    // instantiate a new Query object with the query string set
    val queryObject = new Query("{ " + repositoryQueryBuilder.construct() + " }")

    // set return string to indicate that this query object returns a repository
    queryObject.returnType = "repository"
    queryObject
  }

  /**
   * Returns a [[Query]] object for searching across all '''repositories'''. Query syntax
   * given in [[https://developer.github.com/v4/query/#connections]]
   *
   * @param searchQueryBuilder [[SearchQueryBuilder]] object that contains functions to
   *                          build a search query for all repositories
   * @return object of type [[Query]]
   */
  def searchRepositories(searchQueryBuilder: SearchQueryBuilder): Query = {
    val queryObject = new Query("{ " + searchQueryBuilder.construct() + " }")

    // set return string to indicate that this query object returns a search result
    queryObject.returnType = "search"
    queryObject
  }

}
