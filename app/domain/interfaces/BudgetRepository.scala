package domain.interfaces

import domain.entities.{Budget, FEither}

trait BudgetRepository {
  def insertBudget(budgetHeader: Budget): FEither[Unit]
}
