package builders.queryBuilders

import builders.{PaginationValue, QueryBuilder}

/**
 * Query builder for building sub-fields for a repository topic. Possible subfields and
 * connections given in [[https://developer.github.com/v4/object/repositorytopic/]]
 *
 * @param scalars list of scalars
 * @param fields list of fields
 * @param connections list of connections
 */
case class RepositoryTopicQueryBuilder(scalars: List[String] = List(),
                                       fields: List[QueryBuilder] = List(),
                                       connections: List[QueryBuilder] = List()) extends QueryBuilder {

  // overridden top query
  override var topQuery: String = "repositoryTopic"

  /**
   * Returns a new [[RepositoryTopicQueryBuilder]] object with a modified top query
   *
   * @param queryBuilder object of type [[RepositoryTopicQueryBuilder]]
   * @return object of type [[RepositoryTopicQueryBuilder]]
   */
  private def modifyTopQuery(queryBuilder: RepositoryTopicQueryBuilder): RepositoryTopicQueryBuilder = {
    queryBuilder.topQuery = this.topQuery
    queryBuilder
  }

  /**
   * Includes the '''owner''' field in the repository query
   *
   * @param topicQueryBuilder object of type [[TopicQueryBuilder]] that is used
   *                          to specify the sub-fields of topic
   * @return object of type [[RepositoryTopicQueryBuilder]]
   */
  def includeTopic(topicQueryBuilder: TopicQueryBuilder): RepositoryTopicQueryBuilder = {
    val repositoryTopicQueryBuilder: RepositoryTopicQueryBuilder = new RepositoryTopicQueryBuilder(
      this.scalars,
      topicQueryBuilder :: this.fields,
      this.connections
    )
    this.modifyTopQuery(repositoryTopicQueryBuilder)
  }

}
