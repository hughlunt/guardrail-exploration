package persistence

import java.time.Instant
import java.util.UUID

import com.gu.scanamo.error.MissingProperty
import domain.entities._
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.scalatest.mockito._
import org.scalatest.{AsyncFlatSpec, Matchers}

import scala.concurrent.{ExecutionContext, Future}

class BudgetRepositoryImplSpec extends AsyncFlatSpec with Matchers with MockitoSugar {

  val budgetId = BudgetId(UUID.randomUUID())
  val clinicalTrialAgreementId = ClinicalTrialAgreementId(UUID.randomUUID())

  val mockDynamoClient: DynamoClient = mock[DynamoClient]
  val budgetRepository: BudgetRepositoryImpl = new BudgetRepositoryImpl(mockDynamoClient)

  val dummyBudgetHeader: BudgetHeader = BudgetHeader(
    budgetId,
    clinicalTrialAgreementId,
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    Instant.now(),
    Instant.now()
  )

  it should "Insert given Budget header into DB" in {
    when(mockDynamoClient.runScanamoAsync(any())(any[ExecutionContext]))
      .thenReturn(Future.successful(None))

    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Right(()))
  }

  it should "Overwrite given Budget header into DB" in {
    when(mockDynamoClient.runScanamoAsync[BudgetHeader](any())(any[ExecutionContext]))
      .thenReturn(Future.successful(Some(Right(dummyBudgetHeader))))

    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Right(()))
  }

  it should "Return error given Budget header into DB" in {
    when(mockDynamoClient.runScanamoAsync[BudgetHeader](any())(any[ExecutionContext]))
      .thenReturn(Future.successful(Some(Left(MissingProperty))))

    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Left(BudgetHeaderWriteError("MissingProperty")))
  }

  it should "Handle failed Future" in {
    when(mockDynamoClient.runScanamoAsync[BudgetHeader](any())(any[ExecutionContext]))
      .thenReturn(Future.failed(new Throwable("Future failed.")))

    val result = budgetRepository.insertBudgetHeader(dummyBudgetHeader)

    result.value.map(_ shouldBe Left(DataBaseConnectionError))
  }
}
