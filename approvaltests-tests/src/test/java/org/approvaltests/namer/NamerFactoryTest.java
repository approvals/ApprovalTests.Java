package org.approvaltests.namer;

import org.approvaltests.Approvals;
import org.approvaltests.namer.MultipleFilesLabeller;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.Test;

public class NamerFactoryTest
{
  @Test
  public void testMultipleFiles()
  {
    try (MultipleFilesLabeller labeller = NamerFactory.useMultipleFiles()) {
      Approvals.verify("one");
      labeller.next();
      Approvals.verify("two");
    }
  }
}
