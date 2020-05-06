package builders.queryBuilders

import builders.QueryBuilder

/**
 * Query builder for building sub-fields for an issue. Possible subfields and
 * connections given in [[https://developer.github.com/v4/object/issue/]]
 *
 * @param scalars list of scalars
 * @param fields list of fields
 * @param connections list of connections
 */
case class IssueQueryBuilder(scalars: List[String] = List(),
                        fields: List[QueryBuilder] = List(),
                        connections: List[QueryBuilder] = List()) extends QueryBuilder {

  // overridden top query
  override var topQuery: String = "issue"

  /**
   * Returns a new [[IssueQueryBuilder]] object with a modified top query
   *
   * @param queryBuilder object of type [[IssueQueryBuilder]]
   * @return object of type [[IssueQueryBuilder]]
   */
  private def modifyTopQuery(queryBuilder: IssueQueryBuilder): IssueQueryBuilder = {
    queryBuilder.topQuery = this.topQuery
    queryBuilder
  }

  /**
   * Includes the '''owner''' field in the repository query
   *
   * @param authorQueryBuilder object of type [[RepositoryOwnerQueryBuilder]] that is
   *                           used to specify the sub-fields of author
   * @return object of type [[IssueQueryBuilder]]
   */
  def includeAuthor(authorQueryBuilder: RepositoryOwnerQueryBuilder): IssueQueryBuilder = {
    authorQueryBuilder.topQuery = "author"
    val issueQueryBuilder: IssueQueryBuilder = new IssueQueryBuilder(
      this.scalars,
      authorQueryBuilder :: this.fields,
      this.connections
    )
    this.modifyTopQuery(issueQueryBuilder)
  }

  /**
   * Includes the '''closed''' field in the issue query
   *
   * @return object of type [[IssueQueryBuilder]]
   */
  def includeIsClosed(): IssueQueryBuilder = {
    val issueQueryBuilder: IssueQueryBuilder = new IssueQueryBuilder(
      "closed" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(issueQueryBuilder)
  }

  /**
   * Includes the '''repository''' field in the issue query
   *
   * @param repositoryQueryBuilder object of type [[RepositoryQueryBuilder]] that is
   *                               used to specify the sub-fields of repository
   * @return object of type [[IssueQueryBuilder]]
   */
  def includeRepository(repositoryQueryBuilder: RepositoryQueryBuilder): IssueQueryBuilder = {
    val issueQueryBuilder: IssueQueryBuilder = new IssueQueryBuilder(
      this.scalars,
      repositoryQueryBuilder :: this.fields,
      this.connections
    )
    this.modifyTopQuery(issueQueryBuilder)
  }

  /**
   * Includes the '''title''' field in the issue query
   *
   * @return object of type [[IssueQueryBuilder]]
   */
  def includeTitle(): IssueQueryBuilder = {
    val issueQueryBuilder: IssueQueryBuilder = new IssueQueryBuilder(
      "title" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(issueQueryBuilder)
  }

  /**
   * Includes the '''url''' field in the issue query
   *
   * @return object of type [[IssueQueryBuilder]]
   */
  def includeUrl(): IssueQueryBuilder = {
    val issueQueryBuilder: IssueQueryBuilder = new IssueQueryBuilder(
      "url" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(issueQueryBuilder)
  }

}
