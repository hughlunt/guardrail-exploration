package domain.interpreter

import java.util.UUID

import domain.entities._
import org.scalatest.{AsyncFlatSpec, Matchers}

class BudgetItemInterpreterSpec extends AsyncFlatSpec with Matchers {

  it should "return error future" in {
    val result: FEither[Unit] = new BudgetItemInterpreter().add(Set())
    result.value.map(either => either shouldBe Left(Error("Error in budget item")))
  }

  it should "return empty budget items" in {

    val actualResult = new BudgetItemInterpreter().retrieveItems(BudgetId(UUID.randomUUID()))
    actualResult.value.map(_ shouldBe Right(Set()))

  }
}
