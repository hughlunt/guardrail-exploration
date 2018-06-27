package domain.programs

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import domain.algebras.{BudgetAlgebra, BudgetItemAlgebra}
import domain.entities.{Budget, BudgetId, ClinicalTrialAgreementId}

import scala.language.higherKinds

class BudgetProgram[F[_] : Monad](budgetAlgebra: BudgetAlgebra[F], budgetItemAlgebra: BudgetItemAlgebra[F]) {

  def addBudget(budget: Budget): F[Unit] =
    for {
      _ <- budgetAlgebra.add(budget)
      _ <- budgetItemAlgebra.add(budget.budgetItems)
    } yield ()

  def retrieveBudget(id: BudgetId): F[Budget] = ???
//    for {
//      header <- budgetAlgebra.retrieve(id)
//      items <- budgetItemAlgebra.retrieveItems(id)
//    } yield Budget(header, items)

  def retrieveBudgets(clinicalTrialAgreementId: ClinicalTrialAgreementId): F[Set[Budget]] =
    budgetAlgebra.retrieveBudgets(clinicalTrialAgreementId)
}
