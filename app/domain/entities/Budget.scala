package domain.entities

import java.time.Instant

case class Budget(id: BudgetId,
                  clinicalTrialAgreementHaha: ClinicalTrialAgreementId,
                  study: StudyId,
                  site: SiteId,
                  validFrom: Instant,
                  validUntil: Instant,
                  budgetItems: Set[BudgetItem])
