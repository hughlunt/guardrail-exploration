package interpreter

import java.time.Instant
import java.util.UUID

import domain.entities._
import org.scalatest.{AsyncFlatSpec, Matchers}

class BudgetHeaderInterpreterSpec extends AsyncFlatSpec with Matchers {

  it should "return successful future" in {

    val dummyBudgetHeader = BudgetHeader(
      BudgetId(UUID.randomUUID()),
      CtaId(UUID.randomUUID()),
      StudyId(UUID.randomUUID()),
      SiteId(UUID.randomUUID()),
      Instant.now(),
      Instant.now()
    )

    val result: FEither[Unit] = new BudgetHeaderInterpreter().add(dummyBudgetHeader)
    result.value.map(either => either shouldBe Right(()))
  }
}
