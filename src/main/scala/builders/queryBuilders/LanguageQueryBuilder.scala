package builders.queryBuilders

import builders.QueryBuilder

/**
 * Query builder for building sub-fields for a language. Possible subfields and
 * connections given in [[https://developer.github.com/v4/object/language/]]
 *
 * @param scalars list of scalars
 * @param fields list of fields
 * @param connections list of connections
 */
case class LanguageQueryBuilder(scalars: List[String] = List(),
                             fields: List[QueryBuilder] = List(),
                             connections: List[QueryBuilder] = List()) extends QueryBuilder {

  // overridden top query
  override var topQuery: String = "language"

  /**
   * Returns a new [[LanguageQueryBuilder]] object with a modified top query
   *
   * @param queryBuilder object of type [[LanguageQueryBuilder]]
   * @return object of type [[LanguageQueryBuilder]]
   */
  def modifyTopQuery(queryBuilder: LanguageQueryBuilder): LanguageQueryBuilder = {
    queryBuilder.topQuery = this.topQuery
    queryBuilder
  }

  /**
   * Includes the '''name''' field in the language query
   *
   * @return object of type [[LanguageQueryBuilder]]
   */
  def includeName(): LanguageQueryBuilder = {
    val languageQueryBuilder: LanguageQueryBuilder = new LanguageQueryBuilder(
      "name" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(languageQueryBuilder)
  }

  /**
   * Includes the '''color''' field in the repository query
   *
   * @return object of type [[LanguageQueryBuilder]]
   */
  def includeColor(): LanguageQueryBuilder = {
    val languageQueryBuilder: LanguageQueryBuilder = new LanguageQueryBuilder(
      "color" :: this.scalars,
      this.fields,
      this.connections
    )
    this.modifyTopQuery(languageQueryBuilder)
  }

}
