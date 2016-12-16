import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import spark.ResponseTransformer
import spark.Spark._

object MainClass {
  val objectMapper = new ObjectMapper with ScalaObjectMapper
  objectMapper.registerModule(DefaultScalaModule)

  val transformer: ResponseTransformer = (model) => objectMapper.writeValueAsString(model)

  case class Person(id: Long, name: String, age: Int)

  def main(args: Array[String]): Unit = {
    port(8080)

    get("/hello", (_, _) => Person(1L, "Gabriel", 23), transformer)

    after("/*", (_, resp) => {
      resp.`type`("application/json")
    })
  }
}
