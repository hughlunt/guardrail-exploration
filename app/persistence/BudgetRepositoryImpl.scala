package persistence

import java.time.Instant
import java.util.UUID

import cats.data.EitherT
import cats.free.Free
import com.gu.scanamo._
import com.gu.scanamo.error.DynamoReadError
import com.gu.scanamo.ops.{ScanamoOps, ScanamoOpsA}
import domain.entities._
import domain.interfaces.BudgetRepository

import scala.concurrent.ExecutionContext

class BudgetRepositoryImpl(dynamoClient: DynamoClient)(implicit ec: ExecutionContext)
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

    val budgetTable = Table[Budget]("Budget")

    val operations: ScanamoOps[Option[Either[DynamoReadError, Budget]]] = for {
      result <- budgetTable.put(Budget(budgetHeader, Set()))
    } yield result


    EitherT(
      ScanamoAsync.exec(dynamoClient.client())(operations).map(
        _.fold[Either[Error, Unit]](Right(())) {
          case Left(error) => Left(BudgetHeaderWriteError(error.toString))
          case Right(_) => Right(())
      }
    ).recover{case e: Throwable => Left(DataBaseConnectionError)}
    )
  }

  override def insertBudgetItems(budgetItems: Set[BudgetItem]): FEither[Unit] = ???
}
