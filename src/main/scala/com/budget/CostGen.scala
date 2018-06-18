package com.budget

import akka.actor.{Actor, ActorLogging, Props}
import com.budget.CostGen.CostGenMessage

class CostGen extends Actor with ActorLogging {
  override def receive: Receive = {
    case CostGenMessage(message) =>
      log.info("Message Case ::Receiving messages from CostGen ")
    case true =>
      log.info("True case ::Receiving messages from CostGen ")
  }
}

object CostGen {
  def props: Props = Props[CostGen]

  final case class CostGenMessage(message: String)

}