package domain.entities

import java.time.Instant

case class Budget(id: BudgetId,
                  ctaId: CtaId,
                  study: StudyId,
                  site: SiteId,
                  validFrom: Instant,
                  validUntil: Instant,
                  budgetItems: Set[BudgetItem])
