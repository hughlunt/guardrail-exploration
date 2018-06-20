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

  object TestBudgetHeaderInterpreter extends BudgetHeaderAlgebra[Log] {
    override def add(budgetHeader: BudgetHeader): Log[Unit] = Writer(List("I've added a BudgetHeader"), ())

    override def retrieve(id: BudgetId): Log[BudgetHeader] = Writer(List("I've retrieved a BudgetHeader"), dummyBudgetHeader)
  }

  object TestBudgetItemInterpreter extends BudgetItemAlgebra[Log] {
    override def add(items: Set[BudgetItem]): Log[Unit] = Writer(List("I've added BudgetItems"), ())

    override def retrieveItems(id: BudgetId): Log[Set[BudgetItem]] = Writer(List("I've retrieved BudgetItems"), items)
  }

  val budgetId = BudgetId(UUID.randomUUID())

  val items = Set(
    BudgetItem(
      BudgetItemId(2),
      budgetId,
      ActivityType(UUID.randomUUID()),
      PayeeId(UUID.randomUUID()),
      Instant.now(),
      Instant.now(),
      1d
    ))

  val dummyBudgetHeader = BudgetHeader(
    budgetId,
    CtaId(UUID.randomUUID()),
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now()
  )

  val dummyBudget = Budget(dummyBudgetHeader, items)

  it should "add a budget" in {
    val actualResult = new BudgetProgram[Log](TestBudgetHeaderInterpreter, TestBudgetItemInterpreter).addBudget(dummyBudget)

    actualResult.value shouldBe ((): Unit)
    actualResult.written shouldBe List("I've added a BudgetHeader", "I've added BudgetItems")
  }

  it should "retrieve budget" in {

    val actualResult = new BudgetProgram[Log](TestBudgetHeaderInterpreter, TestBudgetItemInterpreter).retrieveBudget(budgetId)

    actualResult.value shouldBe dummyBudget
    actualResult.written shouldBe List("I've retrieved a BudgetHeader", "I've retrieved BudgetItems")
  }

}
