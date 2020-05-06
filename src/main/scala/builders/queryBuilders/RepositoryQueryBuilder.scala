package builders.queryBuilders

import builders.{PaginationValue, QueryBuilder}

/**
 * Query builder for building sub-fields for a repository. Possible subfields and
 * connections given in [[https://developer.github.com/v4/object/repository/]]
 *
 * @param scalars list of scalars
 * @param fields list of fields
 * @param connections list of connections
 */
case class RepositoryQueryBuilder(scalars: List[String] = List(),
                                  fields: List[QueryBuilder] = List(),
                                  connections: List[QueryBuilder] = List()) extends QueryBuilder {

  // overridden top query
  override var topQuery: String = "repository"

  /**
   * Returns a new [[RepositoryQueryBuilder]] object with a modified top query
   *
   * @param queryBuilder object of type [[RepositoryQueryBuilder]]
   * @return object of type [[RepositoryQueryBuilder]]
   */
  private def modifyTopQuery(queryBuilder: RepositoryQueryBuilder): RepositoryQueryBuilder = {
    queryBuilder.topQuery = this.topQuery
    queryBuilder
  }

  /**
   * Includes the '''name''' field in the repository query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeName(): RepositoryQueryBuilder = {
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      "name" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

  /**
   * Includes the '''url''' field in the repository query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeUrl(): RepositoryQueryBuilder = {
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      "url" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

  /**
   * Includes the '''createdAt''' field in the repository query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeDateTime(): RepositoryQueryBuilder = {
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      "createdAt" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

  /**
   * Includes the '''isFork''' field in the repository query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeIsFork(): RepositoryQueryBuilder = {
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      "isFork" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

  /**
   * Includes the '''owner''' field in the repository query
   *
   * @param repositoryOwnerQueryBuilder object of type [[RepositoryOwnerQueryBuilder]]
   *                                    that is used to specify the sub-fields of
   *                                    repositoryOwner
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeOwner(repositoryOwnerQueryBuilder: RepositoryOwnerQueryBuilder): RepositoryQueryBuilder = {
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      this.scalars,
      repositoryOwnerQueryBuilder :: this.fields,
      this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

  /**
   * Includes the '''forks''' connections in a repository query
   *
   * @param forkQueryBuilder object of type [[RepositoryQueryBuilder]] that is used to
   *                         specify the sub-fields of the forks connection
   * @param numberOfResults sub-type of [[PaginationValue]] that specifies number of
   *                        results returned
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeForks(
                    forkQueryBuilder: RepositoryQueryBuilder,
                    numberOfResults: PaginationValue
                  ): RepositoryQueryBuilder = {
    forkQueryBuilder.topQuery = "forks" + s"(${numberOfResults.argument})"
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      this.scalars,
      this.fields,
      forkQueryBuilder :: this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

  /**
   * Includes the '''issues''' connections in a repository query
   *
   * @param issueQueryBuilder object of type [[IssueQueryBuilder]] that is used to
   *                          specify the sub-fields of the issues connection
   * @param numberOfResults sub-type of [[PaginationValue]] that specifies number of
   *                        results returned
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeIssues(
                     issueQueryBuilder: IssueQueryBuilder,
                     numberOfResults: PaginationValue
                   ): RepositoryQueryBuilder = {
    issueQueryBuilder.topQuery = "issues" + s"(${numberOfResults.argument})"
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      this.scalars,
      this.fields,
      issueQueryBuilder :: this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

  /**
   * Includes the '''languages''' connections in a repository query
   *
   * @param languageQueryBuilder object of type [[LanguageQueryBuilder]] that is used to
   *                             specify the sub-fields of the languages connection
   * @param numberOfResults sub-type of [[PaginationValue]] that specifies number of
   *                        results returned
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeLanguages(
                        languageQueryBuilder: LanguageQueryBuilder,
                        numberOfResults: PaginationValue
                      ): RepositoryQueryBuilder = {
    languageQueryBuilder.topQuery = "languages" + s"(${numberOfResults.argument})"
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      this.scalars,
      this.fields,
      languageQueryBuilder :: this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

  /**
   * Includes the '''repositoryTopics''' connection in the repository query
   *
   * @param topicQueryBuilder object of type of [[RepositoryTopicQueryBuilder]] that is
   *                          used to specify the sub-fields of the repositoryTopic
   *                          connection
   * @param numberOfResults sub-type of [[PaginationValue]] that specifies number of
   *                        results returned
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeRepositoryTopics(
                               topicQueryBuilder: RepositoryTopicQueryBuilder,
                               numberOfResults: PaginationValue
                             ): RepositoryQueryBuilder = {
    topicQueryBuilder.topQuery = "repositoryTopics" + s"(${numberOfResults.argument})"
    val repositoryQueryBuilder: RepositoryQueryBuilder = new RepositoryQueryBuilder(
      this.scalars,
      this.fields,
      topicQueryBuilder :: this.connections
    )
    this.modifyTopQuery(repositoryQueryBuilder)
  }

}