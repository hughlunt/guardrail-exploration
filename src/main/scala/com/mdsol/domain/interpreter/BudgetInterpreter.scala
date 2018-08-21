package com.mdsol.domain.interpreter

import cats.data.EitherT
import cats.implicits._
import com.mdsol.domain.algebras.BudgetAlgebra
import com.mdsol.domain.entities.{Budget, BudgetId, ClinicalTrialAgreementId, FEither, Error}

import scala.concurrent.{ExecutionContext, Future}

class BudgetInterpreter(implicit ec: ExecutionContext) extends BudgetAlgebra[FEither] {
  override def add(budgetHeader: Budget): FEither[Unit] = EitherT.right(Future.successful(()))

  override def retrieve(id: BudgetId): FEither[Budget] =
    EitherT[Future, Error, Budget](Future.failed(new Throwable(" Method not implemented ")))

  override def retrieveBudgets(clinicalTrialAgreementId: ClinicalTrialAgreementId): FEither[Set[Budget]] =
    EitherT.right(Future.successful(Set()))

}
