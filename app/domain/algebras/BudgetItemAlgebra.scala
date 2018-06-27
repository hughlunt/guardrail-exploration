package domain.algebras

import domain.entities.{BudgetId, BudgetItem}

import scala.language.higherKinds

trait BudgetItemAlgebra[F[_]] {

  def add(item: List[BudgetItem]): F[Unit]

  def retrieveItems(id: BudgetId): F[List[BudgetItem]]
}
