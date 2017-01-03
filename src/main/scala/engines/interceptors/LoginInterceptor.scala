package engines.interceptors

import akka.actor.ActorLogging
import akka.contrib.pattern.ReceivePipeline
import akka.contrib.pattern.ReceivePipeline.HandledCompletely
import models.events.{Login, Privileged}

/**
  * Created by markmo on 18/11/2016.
  */
trait LoginInterceptor extends ActorLogging {
  this: ReceivePipeline =>

  pipelineInner {
    case ev: Privileged =>
      val sender = ev.sender
      val text = ev.text
      log.debug("need to authenticate")
      self ! Login(ev, sender, text)
      HandledCompletely
  }
}
