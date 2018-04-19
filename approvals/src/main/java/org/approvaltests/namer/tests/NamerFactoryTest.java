package org.approvaltests.namer.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.namer.MultipleFilesLabeller;
import org.approvaltests.namer.NamerFactory;

public class NamerFactoryTest extends TestCase
{
  public void testMultipleFiles() throws Exception
  {
    MultipleFilesLabeller labeller = NamerFactory.ApprovalResults.useMultipleFiles();
    Approvals.verify("one");
    labeller.next();
    Approvals.verify("two");
  }
}
