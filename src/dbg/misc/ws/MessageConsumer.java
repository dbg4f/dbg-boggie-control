package dbg.misc.ws;

import java.io.IOException;

/**
 * @author bogdel
 */
public interface MessageConsumer {

  void onMessage(String message) throws IOException;

}
