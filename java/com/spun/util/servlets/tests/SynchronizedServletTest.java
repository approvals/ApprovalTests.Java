package com.spun.util.servlets.tests;

import org.lambda.actions.Action0;
import org.lambda.actions.implementations.A0;

import com.spun.util.LambdaThreadLauncher;
import com.spun.util.ThreadLauncher;
import com.spun.util.servlets.PassThrough;
import com.spun.util.servlets.ServletSynchronizer;

import junit.framework.TestCase;

public class SynchronizedServletTest extends TestCase
{
  /***********************************************************************/
  public void test() throws Throwable
  {
    MockSynchronizedServlet.counter = 0;
    ServletSynchronizer syncro = new ServletSynchronizer();
    new LambdaThreadLauncher(Action0.runtime(() -> this.launchServlet(syncro)));
    new LambdaThreadLauncher(Action0.runtime(() -> this.launchServlet(syncro)));
    launchServlet(syncro);
    Thread.sleep(2000);
    assertEquals("only 1 ran", 1, MockSynchronizedServlet.counter);
  }
  /***********************************************************************/
  public void testLambda() throws Throwable
  {
    MockSynchronizedServlet.counter = 0;
    final ServletSynchronizer syncro = new ServletSynchronizer();
    ThreadLauncher.launch(new A0(false, syncro)
    {
      {
        if (a)
        {
          launchServlet(syncro);
        }
      }
    });
    ThreadLauncher.launch(new A0(false, syncro)
    {
      {
        if (a)
        {
          launchServlet(syncro);
        }
      }
    });
    Thread.sleep(2000);
    assertEquals("only 1 ran", 1, MockSynchronizedServlet.counter);
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
