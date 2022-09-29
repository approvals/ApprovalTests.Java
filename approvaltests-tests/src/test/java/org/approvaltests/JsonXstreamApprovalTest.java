package org.approvaltests;

import org.junit.jupiter.api.Test;

import java.time.Instant;

public class JsonXstreamApprovalTest
{
  @Test
  public void testBasicFormatting()
  {
    Circular c = new Circular();
    JsonXtreamApprovals.verifyAsJson(c);
  }

  public static class Circular {
    Circular parent;
    String label;

    public Circular() {
      this.parent = this;
      this.label = "Label";
    }
  }
}
