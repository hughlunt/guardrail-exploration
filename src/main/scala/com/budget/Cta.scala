package com.budget

import akka.actor.{Actor, ActorLogging, Props}
import com.budget.Cta.CtaMessage

/**
  * Created by krapuru on 6/1/18.
  */
class Cta extends Actor with ActorLogging {
  override def receive: Receive = {
    case CtaMessage(message) =>
      log.info("Receiving messages from CTA ")
  }
}

object Cta{

  def props:Props = Props[Cta]
   final case class CtaMessage(message: String)

}

