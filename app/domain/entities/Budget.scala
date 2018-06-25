package domain.entities


case class Budget(id: BudgetId,
                  budgetHeader: BudgetHeader,
                  budgetItems: Set[BudgetItem])
