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

  object TestBudgetHeaderInterpreter extends BudgetAlgebra[Log] {
    override def add(budgetHeader: Budget): Log[Unit] = Writer(List("I've added a BudgetHeader"), ())

    override def retrieve(id: BudgetId): Log[Budget] = Writer(List("I've retrieved a BudgetHeader"), dummyBudget)

    override def retrieveBudgets(clinicalTrialAgreementId: ClinicalTrialAgreementId): Log[Set[Budget]] =
      Writer(List("I've retrieved a BudgetHeader"), Set(dummyBudget))
  }

  object TestBudgetItemInterpreter extends BudgetItemAlgebra[Log] {
    override def add(items: List[BudgetItem]): Log[Unit] = Writer(List("I've added BudgetItems"), ())

    override def retrieveItems(id: BudgetId): Log[List[BudgetItem]] = Writer(List("I've retrieved BudgetItems"), items)
  }

  val budgetId = BudgetId(UUID.randomUUID())
  val clinicalTrialAgreementId = ClinicalTrialAgreementId(UUID.randomUUID())

  val items = List(
    BudgetItem(
      BudgetItemId(2),
      ActivityType(UUID.randomUUID()),
      PayeeId(UUID.randomUUID()),
      Instant.now(),
      Instant.now(),
      1d
    ))

  val dummyBudget = Budget(budgetId,
    clinicalTrialAgreementId,
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now(),
    items)

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

  it should "retrieve a set of budgets given Clinical Trial Agreement Id" in {
    val actualResult = new BudgetProgram[Log](TestBudgetHeaderInterpreter, TestBudgetItemInterpreter).retrieveBudgets(clinicalTrialAgreementId)

    actualResult.value shouldBe Set(dummyBudget)
    actualResult.written shouldBe List("I've retrieved a BudgetHeader")
  }
}
