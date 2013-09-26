package org.jrack.tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.jrack.JRack;
import org.jrack.RackEnvironment;
import org.jrack.utils.InvokerRack;

public class InvokerTest extends TestCase
{
  public void testHelloWorld() throws Exception
  {
    JRack rack = new InvokerRack("org.jrack.*");
    Map<String, Object> input = new HashMap<String, Object>();
    input.put(RackEnvironment.PATH_INFO, "/servlets/org.jrack.tests.HelloWorldRack");
    Approvals.verify(rack.call(input));
  }
  public void testInvalidMask() throws Exception
  {
    assertMask(false, "org.other.*", "org.jrack.tests.HelloWorldRack");
    assertMask(false, "org.other.*", "org.otherproject.BadRack");
    assertMask(true, "org.*.tests.*", "org.jrack.tests.HelloWorldRack");
    assertMask(false, "org.*.nottests.*", "org.jrack.tests.HelloWorldRack");
  }
  private void assertMask(boolean exceptMatch, String mask, String clazz)
  {
    JRack rack = new InvokerRack(mask);
    Map<String, Object> input = new HashMap<String, Object>();
    input.put(RackEnvironment.PATH_INFO, clazz);
    Exception caught = null;
    try
    {
      rack.call(input);
    }
    catch (Exception e)
    {
      caught = e;
    }
    assertEquals(exceptMatch, caught == null);
  }
}
