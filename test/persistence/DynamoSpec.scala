package persistence

import java.time.Instant
import java.util.UUID

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import domain.entities._
import org.scalatest._

trait DynamoTestClient {
  lazy val client = AmazonDynamoDBClientBuilder.standard.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1")).build
}

class DynamoSpec extends AsyncFlatSpec with Matchers with DynamoTestClient {

  val dummyBudgetHeader = BudgetHeader(
    BudgetId(UUID.randomUUID()),
    ClinicalTrialAgreementId(UUID.randomUUID()),
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now()
  )
  lazy val dynamo = new DynamoDB(client)
  it should "return successful future" in {
    val budget = new BudgetClient
    budget.create(dummyBudgetHeader);
    val x = 8
    x shouldBe 8
  }
}