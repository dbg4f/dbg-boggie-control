package dbg.misc.ws;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class MyWebSocketHandler {


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
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
      ticker.session = null;
    }

    @OnWebSocketError
    public void onError(Throwable t) {
      ticker.session = null;
      System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        try {
          session.getRemote().sendString("Hello Web browser");
          ticker.session = session;
          new Thread(ticker).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("Message: " + message);
    }
}