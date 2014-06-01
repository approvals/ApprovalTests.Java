package com.spun.util.servlets.tests;

import junit.framework.TestCase;

import org.lambda.actions.implementations.A0;

import com.spun.util.ThreadLauncher;
import com.spun.util.servlets.PassThrough;
import com.spun.util.servlets.ServletSynchronizer;

public class SynchronizedServletTest
  extends TestCase
{
  /***********************************************************************/
  public void test() throws Throwable
  {
    MockSynchronizedServlet.counter=0;
    ServletSynchronizer syncro = new ServletSynchronizer(); 
    new ThreadLauncher(this, SynchronizedServletTest.class.getMethod("launchServlet",new Class[]{ServletSynchronizer.class}), new Object[]{syncro});
    new ThreadLauncher(this, SynchronizedServletTest.class.getMethod("launchServlet",new Class[]{ServletSynchronizer.class}), new Object[]{syncro});
    launchServlet(syncro);
    Thread.sleep(2000);
    assertEquals("only 1 ran", 1,MockSynchronizedServlet.counter);
    
  }
  /***********************************************************************/
  public void testLambda() throws Throwable
  {
    MockSynchronizedServlet.counter=0;
    final ServletSynchronizer syncro = new ServletSynchronizer(); 
    ThreadLauncher.launch(new A0(false,syncro){{if(a){launchServlet(syncro);}}});
    ThreadLauncher.launch(new A0(false,syncro){{if(a){launchServlet(syncro);}}});
    Thread.sleep(2000);
    assertEquals("only 1 ran", 1,MockSynchronizedServlet.counter);
    
  }
  /***********************************************************************/
  
  public String launchServlet(ServletSynchronizer sync) throws InterruptedException, Throwable
  {
    PassThrough pass = new PassThrough("MyKey", new MockSynchronizedServlet());
    sync.queueServlet(pass);
    return pass.getResponse();
  }
 
  /***********************************************************************/
  /***********************************************************************/
  
}
