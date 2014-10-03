package dbg.misc.ws;

import dbg.misc.ws.hidapi.HIDAPITest;

/**
 * Created by Dmitri on 10/3/2014.
 */
public class JettyWithHidApi {

    public static void main(String[] args) throws Exception {

        HIDAPITest.launch();

        JettyFileServer.main(new String[0]);

    }

}
