package org.approvaltests.packagesettings.subdirectory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class TestApprovalDirectoryConfig
{
  @Test
  public void testApprovalSubdirectory()
  {
    String path = Approvals.createApprovalNamer().getSourceFilePath();
    assertTrue(path.endsWith(File.separator + "approvals" + File.separator));
  }
}
