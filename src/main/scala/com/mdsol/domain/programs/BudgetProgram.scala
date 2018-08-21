package com.mdsol.domain.programs

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import com.mdsol.domain.algebras.{BudgetAlgebra, BudgetItemAlgebra}
import com.mdsol.domain.entities.{Budget, BudgetId, ClinicalTrialAgreementId}

import scala.language.higherKinds

class BudgetProgram[F[_] : Monad](budgetAlgebra: BudgetAlgebra[F], budgetItemAlgebra: BudgetItemAlgebra[F]) {

  def addBudget(budget: Budget): F[Unit] =
    for {
      _ <- budgetAlgebra.add(budget)
      _ <- budgetItemAlgebra.add(budget.budgetItems)
    } yield ()

  def retrieveBudget(id: BudgetId): F[Budget] =
    for {
      budget <- budgetAlgebra.retrieve(id)
    } yield budget

  def retrieveBudgets(clinicalTrialAgreementId: ClinicalTrialAgreementId): F[Set[Budget]] =
    budgetAlgebra.retrieveBudgets(clinicalTrialAgreementId)
}
