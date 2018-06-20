package domain.algebras

import domain.entities.Budget
import scala.language.higherKinds

trait BudgetAlgebra[F[_]] {

  def add(budget: Budget): F[Unit]

}
