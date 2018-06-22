package persistence

import java.time.Instant
import java.util.UUID

import domain.entities._
import org.scalatest.{AsyncFlatSpec, Matchers}

class BudgetRepositorySpec extends AsyncFlatSpec with Matchers {

  val budgetId = BudgetId(UUID.randomUUID())
  val clinicalTrialAgreementId = ClinicalTrialAgreementId(UUID.randomUUID())

  val budgetRepository = new BudgetRepositoryImpl()
  val dummyBudgetHeader = BudgetHeader(
    budgetId,
    clinicalTrialAgreementId,
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now()
  )

  it should "Insert given Budget header into DB" in {
    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)
    result.value.map(_ shouldBe Right(()))
  }

}
