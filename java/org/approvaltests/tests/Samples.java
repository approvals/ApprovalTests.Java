package org.approvaltests.tests;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;

import junit.framework.TestCase;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class Samples extends TestCase
{
  public void testString() throws Exception
  {
    String s = "Approval";
    s += "Tests";
    Approvals.verify(s);
  }
  public void testObject() throws Exception
  {
    MyStringBuilder s = new MyStringBuilder();
    s.append("Approval");
    s.append("Tests");
    Approvals.verify(s.toString());
  }
  public void testArray() throws Exception
  {
    String[] s = new String[2];
    s[0] = "Approval";
    s[1] = "Tests";
    Approvals.verifyAll("Text", s);
  }
}
