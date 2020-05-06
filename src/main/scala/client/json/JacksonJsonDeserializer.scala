package client.json

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}


/**
 * The simple readValue API of the ObjectMapper+ScalaObjectMapper is a good entry point.
 * We are using it to parse or deserialize JSON content into a Scala object.
 */
class JacksonJsonDeserializer(val mapper: ObjectMapper with ScalaObjectMapper) extends JsonDeserializer{

  /**
   *
   * @param json String in JSON format
   * @tparam T the scala case class that is passed to which the json string is deserialize
   * @return The object of the case class to which the json parts are mapped
   */
  override def deserialize[T:Manifest](json: String): T = {
    mapper.readValue[T](json)
  }
}

/**
 * Object Mapper details and properties are tagged to the JacksonJsonDeserializer class using
 * the Companion Object.DefaultScala Module is mapped with the deserializer.It is Singleton so the
 * its members can be accessed directly.
 */
object JacksonJsonDeserializer{

  /**
   * The build function is used to create an instance of JacksonJsonDeserializer.
   * @return Instance of JacksonJsonDeserializer
   */
  def build:JacksonJsonDeserializer={
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    new JacksonJsonDeserializer(mapper)
  }
}
