package builders

/**
 * Defines a pagination operation that specifies how many results will be returned.
 * Contains an argument string that is overridden to return the string version of
 * pagination argument. Implemented by -
 *    - [[First]]
 *    - [[Last]]
 */
sealed trait PaginationValue {
  def argument: String
}

/**
 * Paginating from the front
 *
 * @param number number of results to return
 */
case class First(number: Int) extends PaginationValue {
  override val argument: String = s"first:$number"
}

/**
 * Paginating from the back
 *
 * @param number number of results to return
 */
case class Last(number: Int) extends PaginationValue {
  override val argument: String = s"last:$number"
}
