package domain.interpreter

import cats.data.EitherT
import cats.implicits._
import domain.algebras.BudgetHeaderAlgebra
import domain.entities
import domain.entities.{BudgetHeader, BudgetId, Error, FEither}

import scala.concurrent.{ExecutionContext, Future}

class BudgetHeaderInterpreter(implicit ec: ExecutionContext) extends BudgetHeaderAlgebra[FEither] {
  override def add(budgetHeader: BudgetHeader): FEither[Unit] = EitherT.right(Future.successful(()))

  override def retrieve(id: BudgetId): FEither[BudgetHeader] =
    EitherT[Future, Error, BudgetHeader](Future.failed(new Throwable(" Method not implemented ")))

  override def retrieveBudgetHeaders(clinicalTrialAgreementId: entities.ClinicalTrialAgreementId): FEither[Set[BudgetHeader]] =
    EitherT.right(Future.successful(Set()))

}
