package builders.queryBuilders

import builders.{PaginationValue, QueryBuilder}

/**
 * Query builder for building sub-fields for a topic. Possible subfields and
 * * connections given in [[https://developer.github.com/v4/object/topic/]]
 *
 * @param scalars list of scalars
 * @param fields list of fields
 * @param connections list of connections
 */
case class TopicQueryBuilder(scalars: List[String] = List(),
                             fields: List[QueryBuilder] = List(),
                             connections: List[QueryBuilder] = List()) extends QueryBuilder {

  // overridden top query
  override var topQuery: String = "topic"

  /**
   * Returns a new [[TopicQueryBuilder]] object with a modified top query
   *
   * @param queryBuilder object of type [[TopicQueryBuilder]]
   * @return object of type [[TopicQueryBuilder]]
   */
  private def modifyTopQuery(queryBuilder: TopicQueryBuilder): TopicQueryBuilder = {
    queryBuilder.topQuery = this.topQuery
    queryBuilder
  }

  /**
   * Includes the '''name''' field in the topic query.
   *
   * @return object of type [[TopicQueryBuilder]]
   */
  def includeName(): TopicQueryBuilder = {
    val topicQueryBuilder: TopicQueryBuilder = new TopicQueryBuilder(
      "name" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(topicQueryBuilder)
  }

  /**
   * Includes the '''stargazers''' connections in a topic query
   *
   * @param stargazerQueryBuilder object of type [[UserQueryBuilder]] that is used to
   *                              specify the sub-fields of the stargazers connection
   * @param numberOfResults sub-type of [[PaginationValue]] that specifies number of
   *                        results returned
   * @return object of type [[TopicQueryBuilder]]
   */
  def includeStargazers(
                         stargazerQueryBuilder: UserQueryBuilder,
                         numberOfResults: PaginationValue
                       ): TopicQueryBuilder = {
    stargazerQueryBuilder.topQuery = "stargazers" + s"(${numberOfResults.argument})"
    val topicQueryBuilder: TopicQueryBuilder = new TopicQueryBuilder(
      this.scalars,
      this.fields,
      stargazerQueryBuilder :: this.connections
    )
    this.modifyTopQuery(topicQueryBuilder)
  }
}
