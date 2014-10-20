package jettysimple;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloHandler extends AbstractHandler
{
    public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String firstname = request.getParameter("firstname");
        System.out.println("firstname = " + firstname);
        System.out.println("lastname = " + request.getParameter("lastname"));

        response.getWriter().println("<form>\n" +
                "First name: <input type=\"text\" name=\"firstname\"><br>\n" +
                "Last name: <input type=\"text\" name=\"lastname\">\n" +
                "<input type=\"submit\" value=\"Submit\">" +
                "<pre>" + firstname + "</pre>" +
                "</form>");
    }
}