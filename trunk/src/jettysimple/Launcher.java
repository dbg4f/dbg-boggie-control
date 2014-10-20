package jettysimple;

import org.eclipse.jetty.server.Server;

public class Launcher {


    public static void main(String[] args) throws Exception {



        Server server = new Server(8080);

        server.setHandler(new HelloHandler());

        server.start();



    }

}
