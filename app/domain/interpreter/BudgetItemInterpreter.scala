package domain.interpreter

import cats.data.EitherT
import cats.implicits._
import domain.algebras.BudgetItemAlgebra
import domain.entities._

import scala.concurrent.{ExecutionContext, Future}

class BudgetItemInterpreter(implicit ec: ExecutionContext) extends BudgetItemAlgebra[FEither] {

  override def add(item: List[BudgetItem]): FEither[Unit] =
    EitherT.left(Future.successful(DataBaseConnectionError))

  override def retrieveItems(id: BudgetId): FEither[Set[BudgetItem]] =
    EitherT.right(Future.successful(Set()))
}
