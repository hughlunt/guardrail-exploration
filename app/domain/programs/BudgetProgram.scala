package domain.programs

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import domain.algebras.{BudgetHeaderAlgebra, BudgetItemAlgebra}
import domain.entities.{Budget, BudgetHeader, BudgetId, ClinicalTrialAgreementId}

import scala.language.higherKinds

class BudgetProgram[F[_] : Monad](budgetAlgebra: BudgetHeaderAlgebra[F], budgetItemAlgebra: BudgetItemAlgebra[F]) {

  def addBudget(budget: Budget): F[Unit] =
    for {
      _ <- budgetAlgebra.add(budget.budgetHeader)
      _ <- budgetItemAlgebra.add(budget.budgetItems)
    } yield ()

  def retrieveBudget(id: BudgetId): F[Budget] =
    for {
      header <- budgetAlgebra.retrieve(id)
      items <- budgetItemAlgebra.retrieveItems(id)
    } yield Budget(header, items)

  def retrieveBudgets(clinicalTrialAgreementId: ClinicalTrialAgreementId): F[Set[BudgetHeader]] =
    budgetAlgebra.retrieveBudgetHeaders(clinicalTrialAgreementId)
}
