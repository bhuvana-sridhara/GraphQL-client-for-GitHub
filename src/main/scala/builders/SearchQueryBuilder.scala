package builders

import builders.queryBuilders.RepositoryQueryBuilder

/**
 * Defines a query builder for builder query string for a search query on repositories.
 * The search query syntax takes a query string as an argument. This syntax is given in
 * [[https://help.github.com/en/github/searching-for-information-on-github/searching-for-repositories]].
 * The builder includes functions to build the query argument for the search query as well
 * as a function to specify the sub-fields of the repositories that are returned
 *
 * @param numberOfResults pagination object of type [[PaginationValue]]
 * @param connections list of type [[QueryBuilder]] for storing the sub-fields
 *                    of the repository
 * @param queryString string representing the query argument
 * @param after specifies the cursor to return the results after
 */
case class SearchQueryBuilder(
                               numberOfResults: PaginationValue,
                               connections: List[QueryBuilder] = List(),
                               queryString: String = "",
                               after: String = ""
                             ) {

  // type argument of a search query
  private var typeString: String = "type:REPOSITORY"

  // after argument of a search query
  private var afterString: String = s"""after: \"$after\""""

  /**
   * Returns the constructed query string. Uses the arguments as well as sub-fields to
   * build a search query string
   *
   * @return string representing the search query
   */
  def construct(): String = {

    // append arguments of the search query
    var returnString: String =
      s"""search(query: \"$queryString\", $typeString, ${numberOfResults.argument}"""

    // append endCurson if provided
    if (!this.after.equals("")) {
      returnString = returnString + s", $afterString"
    }

    returnString = returnString + ") {"

    // append sub-fields of the repository
    for (connection <- this.connections) {
      returnString = returnString + " nodes { " + connection.construct() + " }"
    }

    // append pageInfo to return the endCursor
    returnString = returnString + " pageInfo { endCursor } }"

    returnString
  }

  /**
   * Deals with the query argument of a search query.
   * Sets search terms. Can take variable number of arguments
   *
   * @param terms the search terms
   * @return object of type [[SearchQueryBuilder]]
   */
  def setSearchTerms(terms: String*): SearchQueryBuilder = {
    var newString: String = this.queryString
    for (term <- terms) {
      newString = newString + " " + term
    }
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Sets content in which the search terms are looked for. Can take variable number
   * of arguments
   *
   * @param contents object of type [[SearchQueryBuilder.Content]]. See possible values
   *                 in companion object
   * @return object of type [[SearchQueryBuilder]]
   */
  def setSearchInContent(contents: SearchQueryBuilder.Content*): SearchQueryBuilder = {
    var newString = this.queryString + " in:"
    for (content <- contents) {
      newString = newString + content + ","
    }
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Sets the user who's repositories need to be searched.
   *
   * @param userName the GitHub username
   * @return object of type [[SearchQueryBuilder]]
   */
  def setSearchInUser(userName: String): SearchQueryBuilder = {
    var newString = this.queryString + s" user:$userName"
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Sets the organization who's repositories need to be searched.
   *
   * @param orgName the organization name in GitHub
   * @return object of type [[SearchQueryBuilder]]
   */
  def setSearchInOrg(orgName: String): SearchQueryBuilder = {
    var newString = this.queryString + s" org:$orgName"
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Filters repository search by the number of followers that the repository has
   *
   * @param operation any of the possible [[Operation]] subtypes
   * @return object of type [[SearchQueryBuilder]]
   */
  def setNumberOfFollowers(operation: Operation): SearchQueryBuilder = {
    var newString = this.queryString + s" followers:${operation.argument}"
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Filters repository search by the number of forks that the repository has
   *
   * @param operation any of the possible [[Operation]] subtypes
   * @return object of type [[SearchQueryBuilder]]
   */
  def setNumberOfForks(operation: Operation): SearchQueryBuilder = {
    var newString = this.queryString + s" forks:${operation.argument}"
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Filters repository search by the number of stars that the repository has
   *
   * @param operation any of the possible [[Operation]] subtypes
   * @return object of type [[SearchQueryBuilder]]
   */
  def setNumberOfStars(operation: Operation): SearchQueryBuilder = {
    var newString = this.queryString + s" stars:${operation.argument}"
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Filters repository search by language. Can take variable number of arguments.
   *
   * @param languages language names
   * @return object of type [[SearchQueryBuilder]]
   */
  def setLanguages(languages: String*): SearchQueryBuilder = {
    var newString = this.queryString + " language:"
    for (language <- languages) {
      newString = newString + language + ","
    }
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Filters repository search by topic
   *
   * @param topic topic name
   * @return object of type [[SearchQueryBuilder]]
   */
  def setTopic(topic: String): SearchQueryBuilder = {
    var newString = this.queryString + s" topic:$topic"
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Filters repository search by the number of topics that the repository has
   *
   * @param operation any of the possible [[Operation]] object
   * @return object of type [[SearchQueryBuilder]]
   */
  def setNumberOfTopics(operation: Operation): SearchQueryBuilder = {
    var newString = this.queryString + s" topics:${operation.argument}"
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the query argument of a search query.
   * Filters repository search by its visibility
   *
   * @param access object of type [[SearchQueryBuilder.Access]]. See companion object for
   *               possible values
   * @return object of type [[SearchQueryBuilder]]
   */
  def setPublicOrPrivate(access: SearchQueryBuilder.Access): SearchQueryBuilder = {
    var newString = this.queryString + s" is:$access"
    new SearchQueryBuilder(
      this.numberOfResults,
      this.connections,
      newString,
      this.after
    )
  }

  /**
   * Deals with the body of the search query for repositories.
   * Constructs the sub-fields of the repository
   *
   * @param repositoryQueryBuilder type [[queryBuilders.RepositoryQueryBuilder]] which has functions to
   *                               specify the subfields of the repository query
   * @return object of type [[SearchQueryBuilder]]
   */
  def includeRepository(repositoryQueryBuilder: RepositoryQueryBuilder): SearchQueryBuilder = {
    repositoryQueryBuilder.topQuery = "... on Repository"
    val searchQueryBuilder = new SearchQueryBuilder(
      this.numberOfResults,
      repositoryQueryBuilder :: this.connections,
      this.queryString,
      this.after
    )
    searchQueryBuilder
  }

}

/**
 * Companion object for [[SearchQueryBuilder]] that contains utility type aliases
 */
case object SearchQueryBuilder {

  type Content = String
  val NAME: Content = "name"
  val DESCRIPTION: Content = "description"
  val README: Content = "readme"

  type Access = String
  val PUBLIC: Access = "public"
  val PRIVATE: Access = "private"

}
