package domain.programs

import java.time.Instant
import java.util.UUID

import cats.data.Writer
import cats.implicits._
import domain.algebras.{BudgetHeaderAlgebra, BudgetItemAlgebra}
import domain.entities._
import org.scalatest.{FlatSpec, Matchers}

class BudgetProgramSpec extends FlatSpec with Matchers {

  type Log[A] = Writer[List[String], A]

  object TestBudgetHeaderInterpreter$ extends BudgetHeaderAlgebra[Log] {
    override def add(budgetHeader: BudgetHeader): Log[Unit] = Writer(List("I've added a Budget"), ())

    override def retrieve(id: BudgetId): Log[BudgetHeader] = ???
  }

  object TestBudgetItemInterpreter extends BudgetItemAlgebra[Log] {
    override def add(items: Set[BudgetItem]): Log[Unit] = Writer(List("I've added a Budget Item"), ())

    override def retrieve(id: BudgetId): Log[Set[BudgetItem]] = ???
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

    val dummyBudgetHeader = BudgetHeader(
      BudgetId(UUID.randomUUID()),
      CtaId(UUID.randomUUID()),
      StudyId(UUID.randomUUID()),
      SiteId(UUID.randomUUID()),
      Instant.now(),
      Instant.now()
    )

    val dummyBudget = Budget(dummyBudgetHeader, items)

    val actualResult = new BudgetProgram[Log](TestBudgetHeaderInterpreter$, TestBudgetItemInterpreter).addBudget(dummyBudget)

    actualResult.value shouldBe ((): Unit)
    actualResult.written shouldBe List("I've added a Budget", "I've added a Budget Item")
  }
}
