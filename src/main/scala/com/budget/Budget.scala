package com.budget

import akka.actor.{ActorRef, ActorSystem}
import com.budget.CostGen.CostGenMessage
import com.budget.Cta.CtaMessage

/**
  * Created by krapuru on 6/1/18.
  */

object Budget extends App {
  // Create the 'helloBudget' actor system
  val system: ActorSystem = ActorSystem("helloBudget")

  //#create-actors
  // Create the CTA actor
  val cta: ActorRef = system.actorOf(Cta.props, "ctaActor")
  val costGen: ActorRef = system.actorOf(CostGen.props, "costGen")

  cta ! CtaMessage("ctaMessage")
  cta ! true
  costGen ! CostGenMessage("costGenMessage")
  costGen ! true


}
