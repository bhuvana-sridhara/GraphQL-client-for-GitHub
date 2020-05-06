package client.json
import com.google.gson.{Gson, JsonObject}

class GsonJsonSerializer(gson:Gson) extends JsonSerializer {
  /**
   * Contains gson serialization method.
   * @param jsonObj object of type json
   * @return A String type is returned
   */
  override def serialize(jsonObj:JsonObject):String= gson.toJson(jsonObj)


}

/**
 * Companion object for [[GsonJsonSerializer]]
 */
object GsonJsonSerializer{
  /**
   * Builds an instance [[GsonJsonSerializer]]
   * @return
   */
  def build:GsonJsonSerializer={
    val gson = new Gson()
    new GsonJsonSerializer(gson)
  }
}
