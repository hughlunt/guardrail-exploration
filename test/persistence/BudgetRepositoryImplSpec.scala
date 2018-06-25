package persistence

import java.time.Instant
import java.util.UUID

import com.gu.scanamo.{ScanamoAsync, _}
import com.gu.scanamo.error.DynamoReadError
import com.gu.scanamo.ops.ScanamoOps
import domain.entities._
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.scalatest.mockito._
import org.scalatest.{AsyncFlatSpec, Matchers}

import scala.concurrent.Future


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
    type ScanOps[A] = ScanamoOps[Option[Either[DynamoReadError, A]]]

    when(ScanamoAsync.exec(dynamoDBClient.client())(any[ScanOps[BudgetHeader]]())) thenReturn Future.successful(None)
    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Right(()))
  }
}
