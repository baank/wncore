package modules.conversation

import akka.actor.Actor
import com.google.inject.AbstractModule
import com.google.inject.name.Names
import engines._
import example.BuyConversationActor
import modules.akkaguice.AkkaGuiceSupport
import net.codingwell.scalaguice.ScalaModule
import services._

import scala.concurrent.duration._

/**
  * Created by markmo on 30/07/2016.
  */
class ConversationModule extends AbstractModule with ScalaModule with AkkaGuiceSupport {

  override def configure(): Unit = {
    bind[Actor].annotatedWith(Names.named(ConciergeActor.name)).to[ConciergeActor]
    bind[Actor].annotatedWith(Names.named(FacebookSendQueue.name)).to[FacebookSendQueue]
    bind[Actor].annotatedWith(Names.named(LiveEngageMessagingActor.name)).to[LiveEngageMessagingActor]
    bind[Actor].annotatedWith(Names.named(LiveEngageChatActor.name)).to[LiveEngageChatActor]
    bind[MessagingProvider].annotatedWith(Names.named(FacebookService.name)).to[FacebookService]
    bind[Conversation].to[ConversationService]
    bindActorFactory[GreetActor, GreetActor.Factory]
    bindActorFactory[BuyConversationActor, BuyConversationActor.Factory]
    bindActorFactory[AgentConversationActor, AgentConversationActor.Factory]
    bindActorFactory[FormActor, FormActor.Factory]
    bindActorFactory[WatsonConversationActor, WatsonConversationActor.Factory]
    bindActorFactory[WvaConversationActor, WvaConversationActor.Factory]
    bind[FiniteDuration].annotatedWith(Names.named("redisConnectTimeout")).toInstance(2 seconds)
  }

}
