package interpreter

import cats.data.EitherT
import cats.implicits._
import domain.algebras.BudgetAlgebra
import domain.entities.{BudgetHeader, FEither}

import scala.concurrent.{ExecutionContext, Future}

class BudgetHeaderInterpreter(implicit ec: ExecutionContext) extends BudgetAlgebra[FEither] {
  override def add(budgetHeader: BudgetHeader): FEither[Unit] = EitherT.right(Future.successful(()))
}
