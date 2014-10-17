package dbg.misc.ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author bogdel
 */
public class ServiceHandler extends AbstractHandler {

  Logger log = LoggerFactory.getLogger(ServiceHandler.class);

  public static final String HTML_FORM = "" +
                                         "<form name=\"input\" action=\"service\" method=\"get\">\n" +
                                         "Command: <input type=\"text\" name=\"command\">\n" +
                                         "<input type=\"submit\" value=\"Submit\">\n" +
                                         "</form>"
                                         ;


  private List<String> commands = new ArrayList<>();

  private String listCommands() {
    StringBuilder buf = new StringBuilder();
    for (String command : commands) {
      buf.append(command).append("\n");
    }
    return buf.toString();
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    baseRequest.setHandled(true);

    String command = request.getParameter("command");
    if (command != null) {
      commands.add(new Date() + " " + command);

        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
        try {
            Map<String,String> map = gson.fromJson(command, stringStringMap);
            MessageFlowMediator.getInstance().broadcast(gson.toJson(map));
        }
        catch (Exception e) {
            log.error("Failed to parse json:" + command + " " + e.getMessage(), e);
        }


    }

    response.getWriter().println(HTML_FORM + "<pre>" + listCommands() + "</pre>");
  }
}