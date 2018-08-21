package com.mdsol.api.routes

import com.mdsol.server.budget.{BudgetHandler, BudgetResource}
import com.mdsol.server.definitions.BudgetRequest

import scala.concurrent.Future

class BudgetRoutes extends BudgetHandler {
  override def createBudget(respond: BudgetResource.createBudgetResponse.type)(body: BudgetRequest): Future[BudgetResource.createBudgetResponse] =
    Future.successful(respond.OK)
}
