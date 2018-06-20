package domain.programs

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import domain.algebras.{BudgetAlgebra, BudgetItemAlgebra}
import domain.entities.Budget

import scala.language.higherKinds

class BudgetProgram[F[_] : Monad](budgetAlgebra: BudgetAlgebra[F], budgetItemAlgebra: BudgetItemAlgebra[F]) {

  def addBudget(budget: Budget): F[Unit] =
    for {
      _ <- budgetAlgebra.add(budget.budgetHeader)
      _ <- budgetItemAlgebra.add(budget.budgetItems)
    } yield ()
}
