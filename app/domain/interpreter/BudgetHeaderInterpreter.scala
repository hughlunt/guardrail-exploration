package domain.interpreter

import cats.data.EitherT
import cats.implicits._
import domain.algebras.BudgetHeaderAlgebra
import domain.entities.{BudgetHeader, BudgetId, FEither, Error}

import scala.concurrent.{ExecutionContext, Future}

class BudgetHeaderInterpreter(implicit ec: ExecutionContext) extends BudgetHeaderAlgebra[FEither] {
  override def add(budgetHeader: BudgetHeader): FEither[Unit] = EitherT.right(Future.successful(()))

  override def retrieve(id: BudgetId): FEither[BudgetHeader] =
    EitherT[Future, Error, BudgetHeader](Future.failed(new Throwable(" Method not implemented ")))

}
