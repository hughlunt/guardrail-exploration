package domain.entities

case class Budget(budgetHeader: BudgetHeader,
                  budgetItems: Set[BudgetItem])
