package domain.programs

import java.time.Instant
import java.util.UUID

import cats.Id
import domain.algebras.{BudgetAlgebra, BudgetItemAlgebra}
import domain.entities._
import org.scalatest.{FlatSpec, Matchers}

class BudgetProgramSpec extends FlatSpec with Matchers {

  object TestBudgetInterpreter extends BudgetAlgebra[Id] {
    override def add(budget: Budget): Id[Unit] = ()
  }

  object TestBudgetItemInterpreter extends BudgetItemAlgebra[Id] {
    override def add(items: Set[BudgetItem]): Id[Unit] = ()
  }

  it should "add a budget" in {

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

    new BudgetProgram[Id](TestBudgetInterpreter, TestBudgetItemInterpreter).addBudget(dummyBudget) shouldBe((): Unit)
  }

}
