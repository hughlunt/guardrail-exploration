package com.mdsol.domain.interfaces

import com.mdsol.domain.entities.{Budget, FEither}

trait BudgetRepository {
  def insertBudget(budgetHeader: Budget): FEither[Unit]
}
