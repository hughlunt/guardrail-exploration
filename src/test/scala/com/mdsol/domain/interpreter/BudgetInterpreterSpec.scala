package com.mdsol.domain.interpreter

import java.time.Instant
import java.util.UUID

import com.mdsol.domain.entities._
import org.scalatest.{AsyncFlatSpec, Matchers}

class BudgetInterpreterSpec extends AsyncFlatSpec with Matchers {

  val dummyBudget = Budget(
    BudgetId(UUID.randomUUID()),
    ClinicalTrialAgreementId(UUID.randomUUID()),
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now(),
    Set()
  )

  it should "return successful future" in {
    val result: FEither[Unit] = new BudgetInterpreter().add(dummyBudget)
    result.value.map(either => either shouldBe Right(()))
  }

  it should "return a budget" in {
    recoverToSucceededIf[Throwable] {
      new BudgetInterpreter().retrieve(BudgetId(UUID.randomUUID())).value
    }
  }

  it should "return a budgets  by clinical trail agreement id" in {
    val result = new BudgetInterpreter().retrieveBudgets(ClinicalTrialAgreementId(UUID.randomUUID()))
    result.value.map(either => either shouldBe Right(Set()))
  }
}
