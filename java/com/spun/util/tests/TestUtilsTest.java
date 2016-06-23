package com.spun.util.tests;

import javax.swing.JButton;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;

import junit.framework.TestCase;

@UseReporter({DiffReporter.class, DelayedClipboardReporter.class})
public class TestUtilsTest extends TestCase
{
  public void testApproveComponent() throws Exception
  {
    JButton b = new JButton("Approval Tests Rule");
    b.setSize(150, 20);
    Approvals.verify(b);
  }
}
