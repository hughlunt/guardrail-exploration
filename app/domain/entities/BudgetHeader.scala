package domain.entities

import java.time.Instant

case class BudgetHeader(id: BudgetId,
                        ctaId: ClinicalTrialAgreementId,
                        study: StudyId,
                        site: SiteId,
                        validFrom: Instant,
                        validUntil: Instant)
