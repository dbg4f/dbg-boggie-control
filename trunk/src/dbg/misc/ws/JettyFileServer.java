package dbg.misc.ws;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
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
    Server server = new Server(9090);
    Connector connector = new ServerConnector(server);
    server.addConnector(connector);

    ResourceHandler resourceHandler = createResourceHandler();

    Handler wsHandler = createWsHandler();


    HandlerList handlers = new HandlerList();
    handlers.setHandlers(new Handler[]{wsHandler, resourceHandler, new DefaultHandler()});
    server.setHandler(handlers);

    server.start();
    server.join();
  }

  private static Handler createWsHandler() {
    return new WebSocketHandler() {
      @Override
      public void configure(WebSocketServletFactory factory) {
        factory.register(MyWebSocketHandler.class);
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