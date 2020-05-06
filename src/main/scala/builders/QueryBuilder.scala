package builders

import com.typesafe.scalalogging.LazyLogging

/**
 * Defines behaviour for building any type of a query string. Contains three lists -
 *    - scalars - list of strings that represent a scalar in GraphQL
 *    - fields - list of [[QueryBuilder]] that contains other scalars and connections
 *    - connections - list of [[QueryBuilder]] that contains nodes of a specific
 *    [[models.objects.GraphQLObject]]
 *
 * Defines a method called construct which takes the included scalars/fields/connections
 * and constructs the query string by iterating through them recursively. Extended by -
 *    - [[builders.queryBuilders.RepositoryQueryBuilder]]
 *    - [[builders.queryBuilders.UserQueryBuilder]]
 *    - [[builders.queryBuilders.IssueQueryBuilder]]
 *    - [[builders.queryBuilders.RepositoryOwnerQueryBuilder]]
 *    - [[builders.queryBuilders.LanguageQueryBuilder]]
 *    - [[builders.queryBuilders.RepositoryTopicQueryBuilder]]
 *    - [[builders.queryBuilders.TopicQueryBuilder]]
 */
abstract class QueryBuilder extends LazyLogging {

  // the three lists
  def scalars: List[String]
  def fields: List[QueryBuilder]
  def connections: List[QueryBuilder]

  // describe the string that goes on the top block of a GraphQL query
  var topQuery: String

  // recursive function to construct the query string
  def construct(): String = {

    logger.info("Constructing")
    var returnString: String = topQuery + " {"

    // iterate through scalars
    for (scalar <- this.scalars) {
      returnString = returnString + " " + scalar
    }

    // iterate through fields
    for (field <- this.fields) {
      returnString = returnString + " " + field.construct()
    }

    // iterate through connections
    for (connection <- this.connections) {
      // access the nodes in the connections
      connection.topQuery = connection.topQuery + " { nodes"
      returnString = returnString + " " + connection.construct()
      returnString = returnString + " }"
    }

    returnString + " }"
  }

}
