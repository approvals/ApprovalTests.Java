package org.jrack.utils.tests;

import java.util.Map;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.namer.IdeLabeller;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.namer.OsEnvironmentLabeller;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.utils.JRackExceptionReporter;
import org.lambda.functions.Function0;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class JRackExceptionReporterTest extends TestCase
{
  public static class JRackError implements JRack
  {
    @Override
    public RackResponse call(Map<String, Object> environment) throws Exception
    {
      throw new Error("My purpose is to throw errors");
    }
  }
  public void testExceptions() throws Exception
  {
    RackResponse response = new JRackExceptionReporter(new JRackError()).call(null);
    NamerFactory.asMachineSpecificTest(new OsEnvironmentAndIdeLabeller());
    String html = response.getResponse().toString();
    Approvals.verifyHtml(clearLineNumbers(html));
  }
  private String clearLineNumbers(String html)
  {
    return html.replaceAll(":\\d+", ":[Line Number]");
  }
  public static class OsEnvironmentAndIdeLabeller implements Function0<String>
  {
    @Override
    public String call()
    {
      return new OsEnvironmentLabeller().call() + "_" + new IdeLabeller().call();
    }
  }
}
