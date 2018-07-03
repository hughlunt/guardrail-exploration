package domain.algebras

import domain.entities.{Budget, BudgetId, ClinicalTrialAgreementId}

import scala.language.higherKinds

trait BudgetAlgebra[F[_]] {
  def add(budget: Budget): F[Unit]

  def retrieve(id: BudgetId): F[Budget]

  def retrieveBudgets(clinicalTrialAgreementId: ClinicalTrialAgreementId): F[Set[Budget]]
}
