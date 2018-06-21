package persistence

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}
import domain.entities.BudgetHeader
import persistence.BudgetDBClient.AttributeName

class BudgetClient {
  private val db = new DynamoDB(Regions.US_EAST_1)
  private val table = db.getTable("budget")

  def create(budgetHeader: BudgetHeader): Unit =
    table.putItem(
      new Item()
        .withPrimaryKey(AttributeName.Id, budgetHeader.id)
        .withString(AttributeName.ClinicalTrailId, budgetHeader.ctaId.toString)
    )
}

object BudgetDBClient {
  object AttributeName {
    val Id = "id"
    val ClinicalTrailId = "clinicalTrailId"
  }
}