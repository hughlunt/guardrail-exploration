package domain.algebras

trait BudgetAlgebra[F[_]] {

  def add(budget: Budget): F[Unit]

}
