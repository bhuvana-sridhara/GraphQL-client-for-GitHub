package client

import builders.Query
import scalaj.http.Http
import com.google.gson.{Gson, JsonObject}
import client.json.{GsonDeserializer, GsonJsonSerializer, JacksonJsonDeserializer}
import models.objects.{GraphQLObject, Repository, Search, User}
import scala.reflect.runtime.universe._
import utils.ConfigReader.getConfigDetails
import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

/**
 * The class defines a method that handles http call to the GraphQL server and handles the query output
 * sent back. Handles Json to String and vice versa conversion by calling [[client.json.GsonJsonSerializer]]
 * and [[client.json.GsonDeserializer]]. Handles the mapping of return response of the query to the modeled
 * case classes using [[client.json.JacksonJsonDeserializer]]
 * @param headerPart the http header for the call body
 */
case class HttpClient(headerPart:Map[String,String]= Map()) extends LazyLogging{

  /**
   *The functions handles the http api call to the Graphql api server and handles the mapping to modeled scala
   * classes
   * @param queryObj built query using the Query Builder classes
   * @param b implicit param to determine Type of object that is obtained from the deserialization
   * @tparam T generic type parameter
   * @return Option[] type containing Some instance of (Repository,Search or User) or None
   */
  def executeQuery[T <: GraphQLObject](queryObj:Query)(implicit b:TypeTag[T]):Option[T]={
    val jsonQuery:String = queryObj.getQueryString
    val returnType =  queryObj.getReturnType
    val jsonObj = jsonObjectCreation(jsonQuery)
    val jsonAst = GsonJsonSerializer.build.serialize(jsonObj)
    val config:Config = getConfigDetails("reference.conf")
    val response = Http(config.getString("API_ENDPOINT"))
      .headers(
        headerPart
      ).postData(jsonAst)
    val responseJson = GsonDeserializer.build.deserialize(response.asString.body)
    if(!responseJson.has("errors")) {
      val data = responseJson.getAsJsonObject("data").getAsJsonObject(returnType)
      val jsonString = GsonJsonSerializer.build.serialize(data)
      returnType match {
        case "repository" => if(b.tpe.toString == "models.objects.Repository"){
          Some(JacksonJsonDeserializer.build.deserialize[Repository](jsonString).asInstanceOf[T])
        }else{
          logger.error("Casting Error!!!Please provide Repository Type for Casting since the query created is of type Repository")
          None
        }
        case "user" => if(b.tpe.toString == "models.objects.User"){
          Some(JacksonJsonDeserializer.build.deserialize[User](jsonString).asInstanceOf[T])
        }else{
          logger.error("Casting Error!!!Please provide User Type for Casting since the query created is of type User")
          None
        }
        case "search" => if(b.tpe.toString == "models.objects.Search"){
          Some(JacksonJsonDeserializer.build.deserialize[Search](jsonString).asInstanceOf[T])
        }else{
          logger.error("Casting Error!!!Please provide Search Type for Casting since the query created is of type Search")
          None
        }
      }
    }
    else{
      logger.error("Query built in wrong format.Build Query in the proper Format!!!")
      None
    }
  }

  /**
   * The function is used as a support function for [[executeQuery]]. It is used to append query property to Json structure
   * @param jsonString parameter of type String which contains Json formatted data
   * @return Json Object of type jsonObj
   */
  private def jsonObjectCreation(jsonString:String)={
    val jsonObj: JsonObject = new JsonObject()
    jsonObj.addProperty("query", jsonString)
    jsonObj
  }

}
