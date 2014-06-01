package org.approvaltests.tests;

import java.awt.Rectangle;

import javax.swing.JButton;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.namer.ApprovalResults;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class ApprovalsTest extends TestCase
{
  public void testApproveComponent() throws Exception
  {
    ApprovalResults.UniqueForOs();
    JButton b = new JButton("Approval Tests Rule");
    b.setSize(150, 20);
    Approvals.verify(b);
  }
  public void testToString() throws Exception
  {
    Approvals.verify(new Rectangle(5, 10, 100, 200));
  }
}
