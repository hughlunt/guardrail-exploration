package com.mdsol.domain.algebras

import com.mdsol.domain.entities.{BudgetId, BudgetItem}

import scala.language.higherKinds

trait BudgetItemAlgebra[F[_]] {

  def add(item: Set[BudgetItem]): F[Unit]

  def retrieveItems(id: BudgetId): F[Set[BudgetItem]]
}
