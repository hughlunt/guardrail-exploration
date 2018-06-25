package persistence

import java.time.Instant
import java.util.UUID

import cats.data.EitherT
import cats.implicits._
import com.gu.scanamo._
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
    _.toString
  )

  override def insertBudgetHeader(budgetHeader: BudgetHeader): FEither[Unit] = {

    val budgetHeaderTable = Table[BudgetHeader]("BudgetHeaders1")

    val operations = for {
      result <- budgetHeaderTable.put(budgetHeader)
    } yield result

    val client = dynamoClient.client()

    EitherT.fromEither(
      Scanamo.exec(client)(operations)
        .fold[Either[Error, Unit]](Right(())) {
          case Left(error) => Left(BudgetHeaderWriteError(error.toString))
          case Right(_) => Right(())
      }
    )
  }

  override def insertBudgetItems(budgetItems: Set[BudgetItem]): FEither[Unit] = ???
}
