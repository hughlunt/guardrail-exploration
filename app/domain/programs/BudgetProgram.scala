package domain.programs

import domain.algebras.{BudgetAlgebra, BudgetItemAlgebra}

class BudgetProgram[F[_]: Monad](budgetAlgebra: BudgetAlgebra[F], budgetItemAlgebra: BudgetItemAlgebra[F]) {



}
