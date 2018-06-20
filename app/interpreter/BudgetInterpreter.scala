package interpreter

import cats.data.EitherT
import cats.implicits._
import domain.algebras.BudgetAlgebra
import domain.entities.{Budget, Error, FEither}

import scala.concurrent.{ExecutionContext, Future}

class BudgetInterpreter(implicit ec: ExecutionContext) extends BudgetAlgebra[FEither] {
  override def add(budget: Budget): FEither[Unit] =
   EitherT.right(Future.successful(()))

}
