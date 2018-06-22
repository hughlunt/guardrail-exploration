package persistence

import cats.data.EitherT
import cats.implicits._
import com.gu.scanamo.{Scanamo, Table}
import domain.entities._
import domain.interfaces.BudgetRepository

import scala.concurrent.ExecutionContext

class BudgetRepositoryImpl(dynamoClient: DynamoClient)(implicit ec: ExecutionContext)
  extends BudgetRepository {

  override def insertBudgetHeader(budgetHeader: BudgetHeader): FEither[Unit] = {
    val budgetHeaderTable = Table[BudgetHeader]("BudgetHeaders")

    val operations = for {
      result <- budgetHeaderTable.put(budgetHeader)
    } yield result

    EitherT.fromEither(
      Scanamo.exec(dynamoClient.client())(operations)
        .fold[Either[Error, Unit]](Left(DataBaseConnectionError)) {
        case Left(error) => Left(BudgetHeaderWriteError(error.toString))
        case Right(_) => Right(())
      }
    )
  }

  override def insertBudgetItems(budgetItems: Set[BudgetItem]): FEither[Unit] = ???
}
