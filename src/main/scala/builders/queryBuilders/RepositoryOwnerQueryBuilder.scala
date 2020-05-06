package builders.queryBuilders

import builders.QueryBuilder
import com.typesafe.scalalogging.LazyLogging

/**
 * Query builder for building sub-fields for a repository owner. Possible subfields and
 * * connections given in [[https://developer.github.com/v4/object/repositoryowner/]]
 *
 * @param scalars list of scalars
 * @param fields list of fields
 * @param connections list of connections
 */
case class RepositoryOwnerQueryBuilder(scalars: List[String] = List(),
                                       fields: List[QueryBuilder] = List(),
                                       connections: List[QueryBuilder] = List())
  extends QueryBuilder with LazyLogging {

  // overridden top query
  override var topQuery: String = "owner"

  /**
   * Returns a new [[RepositoryOwnerQueryBuilder]] object with a modified top query
   *
   * @param queryBuilder object of type [[RepositoryOwnerQueryBuilder]]
   * @return object of type [[RepositoryOwnerQueryBuilder]]
   */
  private def modifyTopQuery(queryBuilder: RepositoryOwnerQueryBuilder): RepositoryOwnerQueryBuilder = {
    queryBuilder.topQuery = this.topQuery
    queryBuilder
  }

  /**
   * Includes the '''login''' field in the repository owner query
   *
   * @return object of type [[RepositoryOwnerQueryBuilder]]
   */
  def includeLogin(): RepositoryOwnerQueryBuilder = {
    logger.info("Including login")
    val repositoryOwnerQueryBuilder: RepositoryOwnerQueryBuilder = new RepositoryOwnerQueryBuilder(
      "login" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(repositoryOwnerQueryBuilder)
  }

  /**
   * Includes the '''url''' field in the repository owner query
   *
   * @return object of type [[RepositoryOwnerQueryBuilder]]
   */
  def includeUrl(): RepositoryOwnerQueryBuilder = {
    logger.info("Including url")
    val repositoryOwnerQueryBuilder: RepositoryOwnerQueryBuilder = new RepositoryOwnerQueryBuilder(
      "url" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(repositoryOwnerQueryBuilder)
  }

}
