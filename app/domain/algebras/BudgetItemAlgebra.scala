package domain.algebras

import domain.entities.BudgetItem
import scala.language.higherKinds

trait BudgetItemAlgebra[F[_]] {

  def add(item: Set[BudgetItem]): F[Unit]

}
