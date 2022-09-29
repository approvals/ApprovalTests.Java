package org.approvaltests;

import org.junit.jupiter.api.Test;

public class JsonXstreamApprovalTest
{
  @Test
  public void testXstreamCircular()
  {
    Circular middle = Circular.getIndirectCircularReference();
    JsonXstreamApprovals.verifyAsJson(middle);
  }
  @Test
  public void testBasicFormatting()
  {
    Circular c = new Circular(null, "Label");
    c.parent = c;
    JsonXstreamApprovals.verifyAsJson(c);
  }

}
