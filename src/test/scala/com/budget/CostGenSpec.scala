package com.budget

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.budget.Cta.CtaMessage
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class CostGenSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with WordSpecLike
    with BeforeAndAfterAll {
  def this() = this(ActorSystem("CostGenSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "A CTA Actor" should {
    "receive message when instructed to" in {
      val cta = system.actorOf(Cta.props)
      cta ! CtaMessage("Test CTA Message")
    }
  }
}