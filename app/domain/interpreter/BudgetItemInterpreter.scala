package domain.interpreter

import cats.data.EitherT
import cats.implicits._
import domain.algebras.BudgetItemAlgebra
import domain.entities
import domain.entities.{BudgetItem, Error, FEither}

import scala.concurrent.{ExecutionContext, Future}

class BudgetItemInterpreter(implicit ec: ExecutionContext) extends BudgetItemAlgebra[FEither] {

  override def add(item: Set[BudgetItem]): FEither[Unit] =
    EitherT.left(Future.successful(Error("Error in budget item")))

  override def retrieve(id: entities.BudgetId): FEither[Set[BudgetItem]] = ???
}
