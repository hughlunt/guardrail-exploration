package domain.algebras

import domain.entities.{BudgetHeader, BudgetId}

import scala.language.higherKinds

trait BudgetHeaderAlgebra[F[_]] {

  def add(budget: BudgetHeader): F[Unit]

  def retrieve(id: BudgetId): F[BudgetHeader]
}
