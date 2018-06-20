package domain.entities

import java.time.Instant

case class Budget(id: BudgetId,
                  ctaId: CtaId,
                  study: StudyId,
                  site: SiteId,
                  validFrom: Instant,
                  validUntil: Instant,
                  budgetItems: Set[BudgetItem])


case class BudgetItem(id: BudgetItemId,
                      activityType: ActivityType,
                      payee: PayeeId,
                      validFrom: Instant,
                      validUntil: Instant,
                      amount: Double)
