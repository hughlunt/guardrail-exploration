package domain.interpreter

import java.time.Instant
import java.util.UUID

import domain.entities._
import org.scalatest.{AsyncFlatSpec, Matchers}

class BudgetHeaderInterpreterSpec extends AsyncFlatSpec with Matchers {

  val dummyBudgetHeader = BudgetHeader(
    BudgetId(UUID.randomUUID()),
    ClinicalTrialAgreementId(UUID.randomUUID()),
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now()
  )

  it should "return successful future" in {
    val result: FEither[Unit] = new BudgetHeaderInterpreter().add(dummyBudgetHeader)
    result.value.map(either => either shouldBe Right(()))
  }

  it should "return a budget header" in {
    recoverToSucceededIf[Throwable] {
      new BudgetHeaderInterpreter().retrieve(BudgetId(UUID.randomUUID())).value
    }
  }

  it should "return a budget headers  by clinical trail agreement id" in {
    val result = new BudgetHeaderInterpreter().retrieveBudgetHeaders(ClinicalTrialAgreementId(UUID.randomUUID()))
    result.value.map(either => either shouldBe Right(Set()))

  }
}
