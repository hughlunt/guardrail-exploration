package com.mdsol.domain

import java.util.UUID

import cats.data.EitherT

import scala.concurrent.Future

package object entities {

  case class StudyId(studyId: UUID) extends AnyVal

  case class SiteId(siteId: UUID) extends AnyVal

  case class PayeeId(id: UUID) extends AnyVal

  case class BudgetId(id: UUID) extends AnyVal

  case class ClinicalTrialAgreementId(ctaId: UUID) extends AnyVal

  case class ActivityType(id: UUID) extends AnyVal

  case class BudgetItemId(id: Long) extends AnyVal

  type FEither[T] = EitherT[Future, Error, T] // = EitherT[Future[Either[Error, T]]]

  sealed trait Error {
    val message: String
  }

  case class BudgetHeaderWriteError(message: String) extends Error

  case object DataBaseConnectionError extends Error {
    val message: String = "Failed to connect to database."
  }

}
