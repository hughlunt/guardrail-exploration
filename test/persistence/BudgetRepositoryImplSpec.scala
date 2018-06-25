package persistence

import java.time.Instant
import java.util.UUID

import domain.entities._
import org.scalatest.mockito._
import org.scalatest.{AsyncFlatSpec, Matchers}


class BudgetRepositoryImplSpec extends AsyncFlatSpec with Matchers with MockitoSugar {

  val budgetId = BudgetId(UUID.randomUUID())
  val clinicalTrialAgreementId = ClinicalTrialAgreementId(UUID.randomUUID())

  val dynamoDBClient: DynamoClient = new DynamoClient

  val budgetRepository: BudgetRepositoryImpl = new BudgetRepositoryImpl(dynamoDBClient)

  val dummyBudgetHeader = BudgetHeader(
    budgetId,
    clinicalTrialAgreementId,
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now()
  )

  it should "Insert given Budget header into DB" in {
    import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._

    dynamoDBClient.createTable(dynamoDBClient.client())("BudgetHeaders1")('id -> S)

    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Right(()))
  }
}
