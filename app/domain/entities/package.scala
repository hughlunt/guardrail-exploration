package domain

import java.util.UUID

package object entities {

  case class StudyId(id: UUID) extends AnyVal
  case class SiteId(id: UUID) extends AnyVal
  case class PayeeId(id: UUID) extends AnyVal
  case class BudgetId(id: UUID) extends AnyVal
  case class CtaId(id: UUID) extends AnyVal
}
