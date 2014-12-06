package dbg.misc.ws;

import dbg.misc.format.JsonMessagePicker;
import dbg.misc.ws.serial.SerialRead;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * @author bogdel
 */
public class JettyFileServer {

  public static void main(String[] args) throws Exception {

      SerialRead.launch();

      JsonMessagePicker messagePicker = new JsonMessagePicker();

      MessageFlowMediator.getInstance().registerConsumer(messagePicker);

    Server server = new Server(9090);
    Connector connector = new ServerConnector(server);
    server.addConnector(connector);

    ResourceHandler resourceHandler = createResourceHandler();
    Handler wsHandler = createWsHandler();
    ContextHandler contextHandler1 = createContextHandler(new ServiceHandler(), "/service");
    ContextHandler contextHandler2 = createContextHandler(new JsonDataHandler(messagePicker), "/dataset");

    HandlerList handlers = new HandlerList();

    handlers.setHandlers(new Handler[]{
      contextHandler1,
      contextHandler2,
      wsHandler,
      resourceHandler,
      new DefaultHandler()});

    server.setHandler(handlers);

    server.start();
    server.join();
  }

  private static ContextHandler createContextHandler(Handler handler, String contextPath) throws Exception {
    ContextHandler context = new ContextHandler();
    context.setContextPath(contextPath);
    context.setResourceBase(".");
    context.setClassLoader(Thread.currentThread().getContextClassLoader());
    context.setHandler(handler);

    return context;
  }

  private static Handler createWsHandler() {
    return new WebSocketHandler() {
      @Override
      public void configure(WebSocketServletFactory factory) {
        factory.register(JettyWebSocketHandler.class);
      }
    };
  }

  private static ResourceHandler createResourceHandler() {
    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setDirectoriesListed(true);
    resourceHandler.setWelcomeFiles(new String[]{"index.html"});
    resourceHandler.setResourceBase("webapp");
    return resourceHandler;
  }
}