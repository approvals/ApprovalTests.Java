package com.spun.util.tests;

import javax.swing.JButton;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.namer.ApprovalResults;
import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;

@UseReporter({DiffReporter.class, DelayedClipboardReporter.class})
public class TestUtilsTest extends TestCase
{
  public void testApproveComponent() throws Exception
  {
    ApprovalResults.UniqueForOs();
    JButton b = new JButton("Approval Tests Rule");
    b.setSize(150, 20);
    Approvals.verify(b);
  }
}
