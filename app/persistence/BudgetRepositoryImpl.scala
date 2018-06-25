package persistence

import java.time.Instant
import java.util.UUID

import cats.data.EitherT
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.gu.scanamo._
import com.gu.scanamo.error.DynamoReadError
import com.gu.scanamo.ops.ScanamoOps
import domain.entities._
import domain.interfaces.BudgetRepository

import scala.concurrent.ExecutionContext

class BudgetRepositoryImpl(dynamoClient: AmazonDynamoDBAsync)(implicit ec: ExecutionContext)
  extends BudgetRepository {

  implicit val jodaStringFormat = DynamoFormat.coercedXmap[Instant, String, IllegalArgumentException](
    Instant.parse(_)
  )(
    _.toString
  )

  implicit val idFormat = DynamoFormat.coercedXmap[BudgetId, String, IllegalArgumentException](
    s => BudgetId(UUID.fromString(s))
  )(
    _.id.toString
  )

  override def insertBudgetHeader(budgetHeader: BudgetHeader): FEither[Unit] = {

    val budgetTable = Table[BudgetHeader]("Budget")

    val operations: ScanamoOps[Option[Either[DynamoReadError, BudgetHeader]]] = for {
      result <- budgetTable.put(budgetHeader)
    } yield result


    EitherT(
      ScanamoAsync.exec(dynamoClient)(operations).map(
        _.fold[Either[Error, Unit]](Right(())) {
          case Left(error) => Left(BudgetHeaderWriteError(error.toString))
          case Right(_) => Right(())
      }
    ).recover{case e: Throwable => Left(DataBaseConnectionError)}
    )
  }

  override def insertBudgetItems(budgetItems: Set[BudgetItem]): FEither[Unit] = ???
}
