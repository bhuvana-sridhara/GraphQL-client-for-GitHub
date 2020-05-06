package client.json

import com.google.gson.JsonObject

/**
 * Json Serializer trait contains one method definition which needs to be implemented
 * by the classes that extend this trait.
 */
trait JsonSerializer {
  /**
   * The function definition states that it takes in a JSON object and returns a String.
   * @param jsonObj object of type json
   * @return A String type is returned
   */
  def serialize(jsonObj:JsonObject):String

}
