package builders

/**
 * Defines a comparison or a range operation used for composing the search query.
 * Defines a string called argument that implementing methods need to override to
 * specify the string version of the operation that they represent. Implemented by -
 *    - [[GreaterThan]]
 *    - [[GreaterThanEqualTo]]
 *    - [[LesserThan]]
 *    - [[LesserThanEqualTo]]
 *    - [[Between]]
 */
sealed trait Operation {
  def argument: String
}

/**
 * Defines a greater than operation
 *
 * @param number the integer that the query should be greater than
 */
case class GreaterThan(number: Int) extends Operation {
  override val argument: String = s">$number"
}

/**
 * Defines a greater than or equal to operation
 *
 * @param number the integer that the query should be greater than or equal to
 */
case class GreaterThanEqualTo(number: Int) extends Operation {
  override val argument: String = s">=$number"
}

/**
 * Defines a lesser than operation
 *
 * @param number the integer that the query should be lesser than
 */
case class LesserThan(number: Int) extends Operation {
  override val argument: String = s"<$number"
}

/**
 * Defines a lesser than or equal to operation
 *
 * @param number the integer that the query should be lesser than or equal to
 */
case class LesserThanEqualTo(number: Int) extends Operation {
  override val argument: String = s"<=$number"
}

/**
 * Defines a range operation
 *
 * @param n1 the integer that the query should be greater than
 * @param n2 the integer that the query should be lesser than
 */
case class Between(n1: Int, n2: Int) extends Operation {
  override val argument: String = s"$n1..$n2"
}
