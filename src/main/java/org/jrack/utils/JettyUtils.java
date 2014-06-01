package org.jrack.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.jrack.JRack;
import org.jrack.RackServlet;
import com.spun.util.tests.TestUtils;

public class JettyUtils
{
  public static void startJettyRack(int port, JRack rack)
  {
    RackServlet.setDefaultRack(rack);
    startJettyServlet(port, RackServlet.class);
  }
  public static void startJettyServlet(int port, Class servlet)
  {
    try
    {
      Server server = new Server(port);
      ServletHandler handler = new ServletHandler();
      handler.addServletWithMapping(servlet.getName(), "/*");
      server.setHandler(handler);
      server.start();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  public static void startAndLaunch(int port, JRack rack)  
  {
      startJettyRack(port, rack);
      TestUtils.displayFile("http://localhost:" + port);
  }
}
