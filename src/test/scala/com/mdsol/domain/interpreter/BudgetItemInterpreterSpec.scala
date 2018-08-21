package com.mdsol.domain.interpreter

import java.util.UUID

import com.mdsol.domain.entities.{BudgetId, DataBaseConnectionError, FEither}
import org.scalatest.{AsyncFlatSpec, Matchers}

class BudgetItemInterpreterSpec extends AsyncFlatSpec with Matchers {

  it should "return error future" in {
    val result: FEither[Unit] = new BudgetItemInterpreter().add(Set())
    result.value.map(either => either shouldBe Left(DataBaseConnectionError))
  }

  it should "return empty budget items" in {
    val actualResult = new BudgetItemInterpreter().retrieveItems(BudgetId(UUID.randomUUID()))
    actualResult.value.map(_ shouldBe Right(Set()))
  }
}
