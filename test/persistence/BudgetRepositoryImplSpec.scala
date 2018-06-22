package persistence

import java.time.Instant
import java.util.UUID

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.gu.scanamo.Scanamo
import com.gu.scanamo.ops.ScanamoOps
import com.gu.scanamo.request.ScanamoPutRequest
import domain.entities._
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito.when
import org.scalatest.mockito._
import org.scalatest.{AsyncFlatSpec, Matchers}


class BudgetRepositoryImplSpec extends AsyncFlatSpec with Matchers with MockitoSugar {

  val budgetId = BudgetId(UUID.randomUUID())
  val clinicalTrialAgreementId = ClinicalTrialAgreementId(UUID.randomUUID())

  val mockDynamoDBClient: DynamoClient = mock[DynamoClient]
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
//    val scanamoPutRequest: ScanamoPutRequest = new ScanamoPutRequest("", new AttributeValue() ,Option.empty)
//
//    when(Scanamo
//      .exec(mockDynamoDBClient.client())
//       (any[ScanamoOps[ScanamoPutRequest]]))
//      .thenReturn(scanamoPutRequest)

    val fakeClient  = AmazonDynamoDBAsyncClient.asyncBuilder()
      .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("dummy", "credentials")))
      .withEndpointConfiguration(new EndpointConfiguration("http://localhost:8000", "us-east-1"))
      .build()

    when(mockDynamoDBClient.client()) thenReturn fakeClient

    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Right(()))
  }
}
