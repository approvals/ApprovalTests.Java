package org.approvaltests.tests;

import java.awt.Rectangle;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;

import junit.framework.TestCase;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class ApprovalsTest extends TestCase
{
  public void testToString() throws Exception
  {
    Approvals.verify(new Rectangle(5, 10, 100, 200));
  }
}
