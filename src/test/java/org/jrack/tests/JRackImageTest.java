package org.jrack.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.jrack.RackResponse;

public class JRackImageTest extends TestCase
{
  public void testRackImage() throws Exception
  {
    RackResponse call = new JRackImage().call(null);
    Approvals.verify(call);
  }
}
