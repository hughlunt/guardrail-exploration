package persistence

import java.time.Instant
import java.util.UUID

import cats.data.EitherT
import com.gu.scanamo._
import com.gu.scanamo.error.DynamoReadError
import com.gu.scanamo.ops.ScanamoOps
import domain.entities._
import domain.interfaces.BudgetRepository

import scala.concurrent.ExecutionContext

class BudgetRepositoryImpl(dynamoClient: DynamoClient)(implicit ec: ExecutionContext)
  extends BudgetRepository {

  implicit val jodaStringFormat: DynamoFormat[Instant] =
    DynamoFormat.coercedXmap[Instant, String, IllegalArgumentException](
      Instant.parse(_)
    )(
      _.toString
    )

  implicit val idFormat: DynamoFormat[BudgetId] =
    DynamoFormat.coercedXmap[BudgetId, String, IllegalArgumentException](
      s => BudgetId(UUID.fromString(s))
    )(
      _.id.toString
    )

  implicit val setFormat: DynamoFormat[Set[BudgetItem]] =
    DynamoFormat.iso[Set[BudgetItem], List[BudgetItem]](_.toSet)(_.toList)

  override def insertBudget(budget: Budget): FEither[Unit] = {

    val budgetTable = Table[Budget]("Budget")

    val operations: ScanamoOps[Option[Either[DynamoReadError, Budget]]] = for {
      result <- budgetTable.put(budget)
    } yield result

    EitherT(
      dynamoClient.runScanamoAsync(operations).map(
        _.fold[Either[Error, Unit]](Right(())) {
          case Left(error) => Left(BudgetHeaderWriteError(error.toString))
          case Right(_) => Right(())
        }
      ).recover { case e: Throwable => Left(DataBaseConnectionError) }
    )
  }
}
