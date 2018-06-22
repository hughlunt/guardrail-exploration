package persistence

import java.time.Instant
import java.util.UUID

import com.gu.scanamo.Scanamo
import com.gu.scanamo.ops.{ScanamoOps, ScanamoOpsA}
import domain.entities._
import org.mockito.Mockito.{when}
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

    when(Scanamo.exec(mockDynamoDBClient.client())(any[ScanamoOps[_]])) thenReturn None

    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Right(()))
  }
}
