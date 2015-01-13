package dbg.misc.ws;

import dbg.misc.format.JsonMessagePicker;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bogdel
 */
public class JsonDataHandler extends AbstractHandler {

  Logger log = LoggerFactory.getLogger(JsonDataHandler.class);

    final JsonMessagePicker jsonMessagePicker;

    public JsonDataHandler(JsonMessagePicker messagePicker) {

        jsonMessagePicker = messagePicker;
    }


    @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    baseRequest.setHandled(true);

    if (request.getParameter("markers") != null) {
      response.getWriter().println(jsonMessagePicker.markers());
    }
    else if (request.getParameter("sensors") != null) {
      response.getWriter().println(jsonMessagePicker.sensors());
    }
    else if (request.getParameter("points") != null) {
      response.getWriter().println(jsonMessagePicker.points());
    }
    else if (request.getParameter("positions") != null) {
      response.getWriter().println(jsonMessagePicker.positions());
    }
    else if (request.getParameter("pid") != null) {
      response.getWriter().println(jsonMessagePicker.pid());
    }
    else {
      response.getWriter().println(jsonMessagePicker.log());
    }



  }
}