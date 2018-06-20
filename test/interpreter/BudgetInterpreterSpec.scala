package interpreter

import java.time.Instant
import java.util.UUID

import domain.entities._
import org.scalatest.{AsyncFlatSpec, Matchers}

class BudgetInterpreterSpec extends AsyncFlatSpec with Matchers {

  it should "return successful future" in {

    val items = Set(
      BudgetItem(
        BudgetItemId(2),
        BudgetId(UUID.randomUUID()),
        ActivityType(UUID.randomUUID()),
        PayeeId(UUID.randomUUID()),
        Instant.now(),
        Instant.now(),
        1d
      ))

    val dummyBudget = Budget(
      BudgetId(UUID.randomUUID()),
      CtaId(UUID.randomUUID()),
      StudyId(UUID.randomUUID()),
      SiteId(UUID.randomUUID()),
      Instant.now(),
      Instant.now(),
      items
    )

    val result: FEither[Unit] = new BudgetInterpreter().add(dummyBudget)
    result.value.map(either => either shouldBe Right(()))
  }

}
