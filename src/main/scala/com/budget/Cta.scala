package com.budget

import akka.actor.{Actor, ActorLogging, Props}
import com.budget.Cta.CtaMessage

class Cta extends Actor with ActorLogging {
  override def receive: Receive = {
    case CtaMessage(message) =>
      log.info("Receiving messages from CTA ")
    case true =>
      log.info("True case ::Receiving messages from CTA ")
  }
}

object Cta {

  def props: Props = Props[Cta]

  final case class CtaMessage(message: String)

}

