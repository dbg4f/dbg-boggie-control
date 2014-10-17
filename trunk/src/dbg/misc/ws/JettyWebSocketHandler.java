package dbg.misc.ws;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebSocket
public class JettyWebSocketHandler {

    private Map<Session, MessageConsumer> activeSessions = new LinkedHashMap<>();

    class Ticker implements Runnable {

      public Session session;

      int i = 0;

      @Override
      public void run() {


        try {
          Thread.sleep(3000);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }

        while (session != null) {



          try {

            Thread.sleep(30);

            session.getRemote().sendString(String.format(" i= %d", i++));
          }
          catch (IOException | InterruptedException e) {
            e.printStackTrace();
          }
        }

      }
    }

    Ticker ticker = new Ticker();


    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
      System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
      ticker.session = null;
      staticSession = null;

      if (activeSessions.containsKey(session)) {
        MessageFlowMediator.getInstance().deregisterConsumer(activeSessions.get(session));
      }

    }

    @OnWebSocketError
    public void onError(Throwable t) {
      ticker.session = null;
      staticSession = null;
      System.out.println("Error: " + t.getMessage());
    }



    private static Session staticSession;
    public static void sendToClient(String msg) throws IOException {
        if (staticSession != null) {
            staticSession.getRemote().sendString(msg);
        }
    }

    @OnWebSocketConnect
    public void onConnect(final Session session) {
        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        try {
          session.getRemote().sendString("Hello Web browser");
          ticker.session = session;
          staticSession = session;
          //new Thread(ticker).start();

          activeSessions.put(session, new MessageConsumer() {
            @Override
            public void onMessage(String message) throws IOException {
              session.getRemote().sendString(message);
            }
          });

          MessageFlowMediator.getInstance().registerConsumer(activeSessions.get(session));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("Message: " + message);
    }
}