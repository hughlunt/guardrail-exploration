package domain.entities

import java.time.ZonedDateTime

case class Budget(id: BudgetId, ctaId: CtaId, study: StudyId, site: SiteId, payee: PayeeId, validFrom: ZonedDateTime, validUntil: ZonedDateTime)
