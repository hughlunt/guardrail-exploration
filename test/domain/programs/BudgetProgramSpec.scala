package domain.programs

import java.time.Instant
import java.util.UUID

import cats.data.Writer
import cats.implicits._
import domain.algebras.{BudgetAlgebra, BudgetItemAlgebra}
import domain.entities._
import org.scalatest.{FlatSpec, Matchers}

class BudgetProgramSpec extends FlatSpec with Matchers {

  type Log[A] = Writer[List[String], A]

  object TestBudgetInterpreter extends BudgetAlgebra[Log] {
    override def add(budget: Budget): Log[Unit] = Writer(List("I've added a Budget"), ())
  }

  object TestBudgetItemInterpreter extends BudgetItemAlgebra[Log] {
    override def add(items: Set[BudgetItem]): Log[Unit] = Writer(List("I've added a Budget Item"), ())
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

    val actualResult = new BudgetProgram[Log](TestBudgetInterpreter, TestBudgetItemInterpreter).addBudget(dummyBudget)

    actualResult.value shouldBe ((): Unit)
    actualResult.written shouldBe List("I've added a Budget", "I've added a Budget Item")
  }
}