package client.json

import com.google.gson.{Gson, JsonObject}

/**
 * Defines functionality to convert String to JsonObject
 * @param gson object of class com.google.gson.Gson
 */
class GsonDeserializer(gson:Gson){
  /**
   *Returns a object of type JsonObject using fromJson() of Gson
   * @param json String that is converted to JsonObject
   * @return object of type JsonObject
   */
  def deserialize(json: String): JsonObject = gson.fromJson(json,classOf[JsonObject])

}

/**
 * Companion object for [[GsonDeserializer]] that is use to map Gson object details
 */
object GsonDeserializer{
  /**
   * The function is used to build the instance [[GsonDeserializer]] class which contains deserialize function
   * @return An instance of GsonDeserializer
   */
  def build:GsonDeserializer={
    val gson = new Gson()
    new GsonDeserializer(gson)
  }
}