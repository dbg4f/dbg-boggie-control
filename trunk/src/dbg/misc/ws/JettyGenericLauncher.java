package dbg.misc.ws;

/**
 * @author bogdel
 */

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.net.MalformedURLException;

public class JettyGenericLauncher {


	public static void main(String[] args) throws Exception {
		new JettyGenericLauncher().launch();
	}

	void launch() throws Exception {

		Server server = new Server(9090);

		WebAppContext context = createContext();

		ContextHandlerCollection contexts = new ContextHandlerCollection();

		contexts.addHandler(context);

		server.setHandler(contexts);
		server.setStopAtShutdown(true);

		server.start();
	}

	private WebAppContext createContext() throws MalformedURLException {

		//File projectBase = new File(System.getProperty("user.dir"));

		File warFolder = new File("webapp");
		//String warUrl = warFolder.toURI().toURL().toExternalForm();

		WebAppContext context = new WebAppContext();

		context.setContextPath("/");

		// "plus" configs
		context.setConfigurationClasses(new String[] { "org.eclipse.jetty.webapp.WebInfConfiguration",
				"org.eclipse.jetty.webapp.WebXmlConfiguration", "org.eclipse.jetty.webapp.MetaInfConfiguration",
				"org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration",
				"org.eclipse.jetty.plus.webapp.PlusConfiguration",
				"org.eclipse.jetty.annotations.AnnotationConfiguration",
				"org.eclipse.jetty.webapp.JettyWebXmlConfiguration" });

		// this is needed to get stuff from Eclipse project CLASSPATH
		context.setParentLoaderPriority(true);

		return context;
	}

}