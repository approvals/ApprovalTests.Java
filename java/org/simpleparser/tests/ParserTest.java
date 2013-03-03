package org.simpleparser.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.simpleparser.Parser;

@UseReporter(DiffReporter.class)
public class ParserTest extends TestCase
{
  private int    a = 1;
  private String b = "howdy";
  public void testSimpleParse() throws Exception
  {
    String text = Parser.Parse("This should show \r\n  a = {a} \r\n  b = {b}", this);
    Approvals.verify(text);
  }
}
