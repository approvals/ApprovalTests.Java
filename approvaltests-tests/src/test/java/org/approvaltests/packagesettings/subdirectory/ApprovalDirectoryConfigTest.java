package org.approvaltests.packagesettings.subdirectory;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApprovalDirectoryConfigTest
{
  @Test
  public void testApprovalSubdirectory()
  {
    String path = Approvals.createApprovalNamer().getSourceFilePath();
    assertTrue(path.endsWith(File.separator + "approvals" + File.separator));
  }
}
