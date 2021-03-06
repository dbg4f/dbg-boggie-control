package dbg.misc.ws.client;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Example of a simple Echo Client.
 */
public class SimpleEchoClient {

  public static void main(String[] args) {
    String destUri = "ws://localhost:9090";
    if (args.length > 0) {
      destUri = args[0];
    }
    WebSocketClient client = new WebSocketClient();
    SimpleEchoSocket socket = new SimpleEchoSocket();
    try {
      client.start();
      URI echoUri = new URI(destUri);
      ClientUpgradeRequest request = new ClientUpgradeRequest();
      client.connect(socket, echoUri, request);
      System.out.printf("Connecting to : %s%n", echoUri);
      socket.awaitClose(5, TimeUnit.SECONDS);
    }
    catch (Throwable t) {
      t.printStackTrace();
    }
    finally {
      try {
        client.stop();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

