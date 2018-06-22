package persistence

import domain.entities.{BudgetHeader, BudgetItem, FEither}
import domain.interfaces.BudgetRepository

class BudgetRepositoryImpl(client: DynamoClient) extends BudgetRepository {

  override def insertBudgetHeader(budgetHeader: BudgetHeader): FEither[Unit] = ???


  override def insertBudgetItems(budgetItems: Set[BudgetItem]): FEither[Unit] = ???
}
