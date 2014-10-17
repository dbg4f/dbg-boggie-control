package dbg.misc.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author bogdel
 */
public class MessageFlowMediator {

  private static Logger log = LoggerFactory.getLogger(MessageFlowMediator.class);

  private static final MessageFlowMediator instance = new MessageFlowMediator();

  private Set<MessageConsumer> activeConsumers = new LinkedHashSet<>();

  public static MessageFlowMediator getInstance() {
    return instance;
  }

  public synchronized void broadcast(String message) {

    for (MessageConsumer consumer : activeConsumers) {
      try {
        consumer.onMessage(message);
      }
      catch (Exception e) {
        log.error("Failed to send message to consumer " + consumer + " " + e.getMessage(), e);
      }
    }

    log.info("Message: " + message + " sent to " + activeConsumers.size() +  " consumers");

  }

  public synchronized void registerConsumer(MessageConsumer consumer) {
    log.info("New consumer registered: " + consumer);
    activeConsumers.add(consumer);
  }

  public synchronized void deregisterConsumer(MessageConsumer consumer) {
    log.info("Consumer deregistered: " + consumer);
    activeConsumers.remove(consumer);
  }

}
