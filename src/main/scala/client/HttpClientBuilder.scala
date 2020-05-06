package client
import com.typesafe.config.Config
import scalaj.http.Http
import utils.ConfigReader.getConfigDetails

/**
 * Defines Phantom type implementation for building the http client used to call the GraphQL API Server
 * @param parts contains the http header components(authentication token,Content-Type and Accept type)
 * @tparam T contains Phantom Types for some or all the  components used in the building the HTTP Call.(via mixin)
 *                        The type parameter determines if the all the components exists in the HTTP call.
 */
class HttpClientBuilder[T <: HttpClientBuilder.HttpComponents](parts: Map[String, String] = Map(("Content-Type" -> "application/json"), ("Accept" -> "application/json"))) {

  import HttpClientBuilder.HttpComponents._

  /**
   * The function adds the user authentication token to the header of the http call
   * @param token parameter of type string which contains user auth token
   * @return returns an instance of HttpClientBuilder with authentication token being added to parts parameter
   */
  def addBearerToken(token: String): HttpClientBuilder[T with HttpToken] = {
    val map = parts + ("Authorization"->("Bearer "+token))
    new HttpClientBuilder(map)
  }

  /**
   * Determines if the given client token is valid one or not and builds an instance of HttpClient
   * @param ev implicit parameter used to determine if the http client is build correctly or not
   * @return an Option[] type containing Some instance of HttpClient or instance of None.
   */
  def build(implicit ev: T =:= HttpCall): Option[HttpClient] ={
    val config:Config = getConfigDetails("reference.conf")
    val response:Int = Http(config.getString("API_ENDPOINT"))
      .headers(
        parts
      ).postData("").asString.code
    response match {
      case 200 => Some(HttpClient(parts))
      case _ => {
        println("Please provide a proper Token!!!")
        None
      }
    }

  }


}

/**
 * Companion object for [[HttpClientBuilder]] which is used to define structural State types.*
 */
object HttpClientBuilder {

  sealed trait HttpComponents

  object HttpComponents {

    sealed trait HttpToken extends HttpComponents

    sealed trait HttpEmpty extends HttpComponents

    type HttpCall = HttpEmpty with HttpToken
  }

}
