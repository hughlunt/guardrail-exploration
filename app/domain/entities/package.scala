package domain

import java.util.UUID

import cats.data.EitherT

import scala.concurrent.Future

package object entities {

  case class StudyId(id: UUID) extends AnyVal

  case class SiteId(id: UUID) extends AnyVal

  case class PayeeId(id: UUID) extends AnyVal

  case class BudgetId(id: UUID) extends AnyVal

  case class CtaId(id: UUID) extends AnyVal

  case class ActivityType(id: UUID) extends AnyVal

  case class BudgetItemId(id: Long) extends AnyVal

  type FEither[T] = EitherT[Future, Error, T] // = EitherT[Future[Either[Error, T]]]

  case class Error(message: String) extends AnyVal

}
