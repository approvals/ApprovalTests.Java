package org.jrack.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.jrack.tests.MoreComplexVelocityRack.MoreComplexVelocity;

public class HelloWorldTest extends TestCase
{
  public void testHelloWorld() throws Exception
  {
    Approvals.verify(new HelloWorldRack().call(null));
  }
  public void testHelloVelocity() throws Exception
  {
    Approvals.verify(new HelloVelocityRack().call(null));
  }
  public void testMoreComplexVelocityTemplate() throws Exception
  {
    Approvals.verifyHtml(new MoreComplexVelocity().init("Sam", 55));
  }
}
