package dbg.misc.ws;

import dbg.misc.format.JsonMessagePicker;
import dbg.misc.ws.serial.SerialRead;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bogdel
 */
public class ConsoleHandler extends AbstractHandler {

  Logger log = LoggerFactory.getLogger(ConsoleHandler.class);

    final JsonMessagePicker jsonMessagePicker;

    Configuration cfg;

    public ConsoleHandler(JsonMessagePicker messagePicker) throws IOException {

      jsonMessagePicker = messagePicker;

      cfg = new Configuration(Configuration.VERSION_2_3_21);
      cfg.setDirectoryForTemplateLoading(new File("webapp/ftl"));
      cfg.setDefaultEncoding("UTF-8");
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

    }


    @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {


     String[] requestParts = baseRequest.getRequestURI().split("/");

      if (requestParts.length > 2) {

        String tail = requestParts[2];

        if (tail.equalsIgnoreCase("commands")) {
          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("sendCommands")) {
          String commands = request.getParameter("commands");

          if (commands != null) {

              log.info("commands = " + commands);

              for (String command : commands.split("\n")) {
                  //jsonMessagePicker.onMessage(command);
                  sendCommand(command);
                  try {
                      Thread.sleep(10);
                  } catch (InterruptedException e) {
                      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                  }
              }

          }


          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("reset")) {
          jsonMessagePicker.reset();
          sendCommand("{\"cmd\":\"listFree\"}");
          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("sendReportsOn")) {
          sendCommand("{\"cmd\":\"sendReports\",\"value\":1.0}");
          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("sendReportsOff")) {
          sendCommand("{\"cmd\":\"sendReports\",\"value\":0.0}");
          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("version")) {
          sendCommand("{\"cmd\":\"version\"}");
          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("runMarkers")) {
          sendCommand("{\"cmd\":\"runMarkers\"}");
          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("runSine")) {
          sendCommand("{\"cmd\":\"runSine\"}");
          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("gotoUpper")) {
          jsonMessagePicker.reset();
          sendCommand("{\"cmd\":\"reg\",\"value\":0.13}");
          createCommandsForm(response);
        }
        else if (tail.equalsIgnoreCase("gotoLower")) {
          jsonMessagePicker.reset();
          sendCommand("{\"cmd\":\"reg\",\"value\":0.17}");
          createCommandsForm(response);
        }
        else {
          createBasicFtlForm(response);
        }

      }
      else {
        createBasicFtlForm(response);
      }


    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    baseRequest.setHandled(true);


      // response.getWriter().println(HTML_FORM + "<pre>" + listCommands() + "</pre>");
  }

    private void sendCommand(String command) {
        MessageFlowMediator.getInstance().senToTarget(command, SerialRead.class);
    }

    private void createBasicFtlForm(HttpServletResponse response) throws IOException {
    Map root = new HashMap();
    root.put("user", "Big Joe");
    Map latest = new HashMap();
    root.put("latestProduct", latest);
    latest.put("url", "products/greenmouse.html");

    latest.put("name", "green mouse ");

    fillTemplate(response, root, "test1.ftl");
  }

  private void fillTemplate(HttpServletResponse response, Map root, String name) throws IOException {
    Template temp = cfg.getTemplate(name);

      /* Merge data-model with template */
    Writer out = new OutputStreamWriter(response.getOutputStream());
    try {
      temp.process(root, out);
    }
    catch (TemplateException e) {
      e.printStackTrace();
    }

    response.getOutputStream().flush();
  }

  private void createCommandsForm(HttpServletResponse response) throws IOException {
    Map root = new HashMap();
    root.put("user", "Big Joe");
    Map latest = new HashMap();
    root.put("latestProduct", latest);
    latest.put("url", "products/greenmouse.html");

    latest.put("name", "green mouse ");

      /* Get the template (uses cache internally) */
    fillTemplate(response, root, "commands.ftl");
  }
}