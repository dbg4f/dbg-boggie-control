package dbg.misc.ws;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author bogdel
 */
public class ServiceHandler extends AbstractHandler {

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
      MessageFlowMediator.getInstance().broadcast(command);
    }

    response.getWriter().println(HTML_FORM + "<pre>" + listCommands() + "</pre>");
  }
}