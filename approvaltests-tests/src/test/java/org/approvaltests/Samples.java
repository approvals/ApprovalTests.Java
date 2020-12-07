package org.approvaltests;

import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
  // begin-snippet: parameterized_test
  @ParameterizedTest
  @ValueSource(strings = {"parameter1", "parameter2"})
  void sampleParameterizedTest(String parameter)
  {
    try (NamedEnvironment en = NamerFactory.asMachineSpecificTest(parameter))
    {
      // your code goes here
      Object output = parameter;
      Approvals.verify(output);
    }
  }
  // end-snippet
}
