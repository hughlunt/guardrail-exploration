package com.mdsol.domain.interpreter

import cats.data.EitherT
import cats.implicits._
import com.mdsol.domain.algebras.BudgetItemAlgebra
import com.mdsol.domain.entities.{BudgetId, BudgetItem, DataBaseConnectionError, FEither}

import scala.concurrent.{ExecutionContext, Future}

class BudgetItemInterpreter(implicit ec: ExecutionContext) extends BudgetItemAlgebra[FEither] {

  override def add(item: Set[BudgetItem]): FEither[Unit] =
    EitherT.left(Future.successful(DataBaseConnectionError))

  override def retrieveItems(id: BudgetId): FEither[Set[BudgetItem]] =
    EitherT.right(Future.successful(Set()))
}
