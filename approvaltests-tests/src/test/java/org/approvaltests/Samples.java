package org.approvaltests;

import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class Samples
{
  @Test
  public void testString()
  {
    String s = "Approval";
    s += "Tests";
    Approvals.verify(s);
  }
  @Test
  public void testObject()
  {
    MyStringBuilder s = new MyStringBuilder();
    s.append("Approval");
    s.append("Tests");
    Approvals.verify(s.toString());
  }
  @Test
  public void testArray()
  {
    String[] s = new String[2];
    s[0] = "Approval";
    s[1] = "Tests";
    Approvals.verifyAll("Text", s);
  }
}
