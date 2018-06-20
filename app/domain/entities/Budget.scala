package domain.entities

import java.time.Instant

case class Budget(id: BudgetId,
                  ctaId: CtaId,
                  study: StudyId,
                  site: SiteId,
                  validFrom: Instant,
                  validUntil: Instant,
                  eventCostCollection: Set[BudgetItem])


case class BudgetItem(activityType: String,
                      id: String,
                      payee: PayeeId,
                      validFrom: Instant,
                      validUntil: Instant,
                      amount: Double)
