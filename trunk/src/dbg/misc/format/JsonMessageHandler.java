package dbg.misc.format;

import java.util.Map;

/**
 * @author bogdel
 */
public interface JsonMessageHandler {

  void onMessage(Map<String, String> structure, String rawMessage);

}
