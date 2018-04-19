package com.spun.util.servlets.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.lambda.actions.Action0;

import com.spun.util.LambdaThreadLauncher;
import com.spun.util.servlets.PassThrough;
import com.spun.util.servlets.ServletSynchronizer;

public class SynchronizedServletTest
{
  @Test
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
  @Test
  public void testLambda() throws Throwable
  {
    MockSynchronizedServlet.counter = 0;
    final ServletSynchronizer syncro = new ServletSynchronizer();
    new LambdaThreadLauncher(Action0.runtime(() -> launchServlet(syncro)));
    new LambdaThreadLauncher(Action0.runtime(() -> launchServlet(syncro)));
    Thread.sleep(2000);
    assertEquals("only 1 ran", 1, MockSynchronizedServlet.counter);
  }
  public String launchServlet(ServletSynchronizer sync) throws InterruptedException, Throwable
  {
    PassThrough pass = new PassThrough("MyKey", new MockSynchronizedServlet());
    sync.queueServlet(pass);
    return pass.getResponse();
  }
}
