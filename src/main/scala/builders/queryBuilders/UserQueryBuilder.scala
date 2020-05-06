package builders.queryBuilders

import builders.{PaginationValue, QueryBuilder}
import com.typesafe.scalalogging.LazyLogging

/**
 * Query builder for building sub-fields for a user. Possible subfields and
 * connections given in [[https://developer.github.com/v4/object/user/]]
 *
 * @param scalars list of scalars
 * @param fields list of fields
 * @param connections list of connections
 */
case class UserQueryBuilder(scalars: List[String] = List(),
                        fields: List[QueryBuilder] = List(),
                        connections: List[QueryBuilder] = List())
  extends QueryBuilder with LazyLogging {

  // overridden top query
  override var topQuery: String = "user"

  /**
   * Returns a new [[UserQueryBuilder]] object with a modified top query
   *
   * @param queryBuilder object of type [[UserQueryBuilder]]
   * @return object of type [[UserQueryBuilder]]
   */
  private def modifyTopQuery(queryBuilder: UserQueryBuilder): UserQueryBuilder = {
    queryBuilder.topQuery = this.topQuery
    queryBuilder
  }

  /**
   * Includes the '''company''' field in the user query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeCompany(): UserQueryBuilder = {
    logger.info("Including company")
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      "company" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

  /**
   * Includes the '''createdAt''' field in the user query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeCreatedAt(): UserQueryBuilder = {
    logger.info("Including createdAt")
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      "createdAt" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

  /**
   * Includes the '''email''' field in the user query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeEmail(): UserQueryBuilder = {
    logger.info("Including email")
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      "email" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

  /**
   * Includes the '''login''' field in the user query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeLogin(): UserQueryBuilder = {
    logger.info("Including login")
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      "login" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

  /**
   * Includes the '''name''' field in the user query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeName(): UserQueryBuilder = {
    logger.info("Including name")
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      "name" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

  /**
   * Includes the '''url''' field in the user query
   *
   * @return object of type [[RepositoryQueryBuilder]]
   */
  def includeUrl(): UserQueryBuilder = {
    logger.info("Including url")
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      "url" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

  /**
   * Includes the '''following''' connection in the user query
   *
   * @param followingQueryBuilder object of type [[UserQueryBuilder]] that is used
   *                              to specify the sub-fields of following
   * @param numberOfResults sub-type of [[PaginationValue]] that specifies number of
   *                        results returned
   * @return object of type [[UserQueryBuilder]]
   */
  def includeFollowing(
                        followingQueryBuilder: UserQueryBuilder,
                        numberOfResults: PaginationValue
                      ): UserQueryBuilder = {
    logger.info("Including following connection")
    followingQueryBuilder.topQuery = "following" + s"(${numberOfResults.argument})"
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      this.scalars,
      this.fields,
      followingQueryBuilder :: this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

  /**
   * Includes the '''followers''' connection in the user query
   *
   * @param followersQueryBuilder object of type [[UserQueryBuilder]] that is used
   *                              to specify the sub-fields of followers
   * @param numberOfResults sub-type of [[PaginationValue]] that specifies number of
   *                        results returned
   * @return object of type [[UserQueryBuilder]]
   */
  def includeFollowers(
                        followersQueryBuilder: UserQueryBuilder,
                        numberOfResults: PaginationValue
                      ): UserQueryBuilder = {
    logger.info("Including followers connection")
    followersQueryBuilder.topQuery = "followers" + s"(${numberOfResults.argument})"
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      this.scalars,
      this.fields,
      followersQueryBuilder :: this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

  /**
   * Includes the '''repositories''' connection in the user query
   *
   * @param repositoryQueryBuilder object of type [[RepositoryQueryBuilder]] that is used
   *                               to specify the sub-fields of followers
   * @param numberOfResults sub-type of [[PaginationValue]] that specifies number of
   *                        results returned
   * @return object of type [[UserQueryBuilder]]
   */
  def includeRepositories(
                           repositoryQueryBuilder: RepositoryQueryBuilder,
                           numberOfResults: PaginationValue
                         ): UserQueryBuilder = {
    logger.info("Including repositories connection")
    repositoryQueryBuilder.topQuery = "repositories" + s"(${numberOfResults.argument})"
    val userQueryBuilder: UserQueryBuilder = new UserQueryBuilder(
      this.scalars,
      this.fields,
      repositoryQueryBuilder :: this.connections
    )
    this.modifyTopQuery(userQueryBuilder)
  }

}
