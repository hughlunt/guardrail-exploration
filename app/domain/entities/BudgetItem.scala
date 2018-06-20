package domain.entities

import java.time.Instant

case class BudgetItem(id: BudgetItemId,
                      activityType: ActivityType,
                      payee: PayeeId,
                      validFrom: Instant,
                      validUntil: Instant,
                      amount: Double)

