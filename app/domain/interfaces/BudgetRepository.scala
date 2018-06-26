package domain.interfaces

import domain.entities.{BudgetHeader, BudgetItem, FEither}

trait BudgetRepository {

  def insertBudgetHeader(budgetHeader: BudgetHeader): FEither[Unit]

//  def insertBudgetItems(budgetItems: Set[BudgetItem]): FEither[Unit]
}
