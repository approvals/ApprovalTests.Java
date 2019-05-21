package org.approvaltests.writers.test;

import org.approvaltests.Approvals;
import org.junit.Test;

public class ApprovalXmlWriterTest
{
  @Test
  public void plainXml() throws Exception
  {
    Approvals.verifyXml("<xml><hello/><start>hi</start></xml>");
  }
  @Test
  public void xmlWithAttributes() throws Exception
  {
    Approvals.verifyXml("<xml b=\"123\" a=\"456\"><hello x=\"y\"/><start>hi</start></xml>");
  }
  @Test
  public void xmlWithDeepAttributes() throws Exception
  {
    Approvals
        .verifyXml("<xml b=\"1\" a=\"1\"><branch1 b=\"1\" a=\"1\"/><branch2 b=\"1\" a=\"1\">hi</branch2></xml>");
  }
  @Test
  public void invalidXml() throws Exception
  {
    Approvals.verifyXml("<xml><hello/><start>hi</xml>");
    System.err.println("Note: The previous xml error (</start>) is expected ");
  }
}
