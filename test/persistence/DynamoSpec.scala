package persistence

import org.scalatest._
import com.gu.scanamo._
import com.gu.scanamo.syntax._

class DynamoSpec extends AsyncFlatSpec with Matchers {
  val client = BudgetClient.client()
  import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._
  val farmersTableResult = BudgetClient.createTable(client)("farmer")('name -> S)

  case class Farm(animals: List[String])
  case class Farmer(name: String, age: Long, farm: Farm)

  val table = Table[Farmer]("farmer")

  Scanamo.exec(client)(table.put(Farmer("McDonald", 156L, Farm(List("sheep", "cow")))))

  val result = Scanamo.exec(client)(table.get('name -> "McDonald"))

  result shouldBe Some(Right(Farmer("McDonald",156, Farm(List("sheep", "cow")))))

}