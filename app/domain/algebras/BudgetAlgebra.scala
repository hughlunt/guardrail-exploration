package domain.algebras

import domain.entities.BudgetHeader

import scala.language.higherKinds

trait BudgetAlgebra[F[_]] {

  def add(budget: BudgetHeader): F[Unit]
}
