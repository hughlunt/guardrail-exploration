package persistence

import java.time.Instant
import java.util.UUID

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, PutItemRequest, PutItemResult}
import com.gu.scanamo.error.DynamoReadError
import com.gu.scanamo.ops.ScanamoOps
import domain.entities._
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.scalatest.mockito._
import org.scalatest.{AsyncFlatSpec, Matchers}
import java.util.concurrent.CompletableFuture

import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync

class BudgetRepositoryImplSpec extends AsyncFlatSpec with Matchers with MockitoSugar {

  val budgetId = BudgetId(UUID.randomUUID())
  val clinicalTrialAgreementId = ClinicalTrialAgreementId(UUID.randomUUID())

  val mockDynamoDBClient: AmazonDynamoDBAsync = mock[DynamoClient].client()

  val budgetRepository: BudgetRepositoryImpl = new BudgetRepositoryImpl(mockDynamoDBClient)

  val dummyBudgetHeader = BudgetHeader(
    budgetId,
    clinicalTrialAgreementId,
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now()
  )

  it should "Insert given Budget header into DB" in {
    type ScanOps[A] = ScanamoOps[Option[Either[DynamoReadError, A]]]

//    when(ScanamoAsync.exec(dynamoDBClient.client())(any[ScanOps[BudgetHeader]]())) thenReturn Future.successful(None)

    when(mockDynamoDBClient.putItemAsync(any[PutItemRequest], any[AsyncHandler[PutItemRequest, PutItemResult]]))
      .thenReturn(CompletableFuture.completedFuture(new PutItemResult().addAttributesEntry(budgetId.toString, new AttributeValue("{}"))))

    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Right(()))
  }
}
