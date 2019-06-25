package org.approvaltests.packagesettings.subdirectory;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.approvaltests.Approvals;
import org.junit.Test;

public class TestApprovalDirectoryConfig
{
  @Test
  public void testApprovalSubdirectory()
  {
    String path = Approvals.createApprovalNamer().getSourceFilePath();
    //    System.out.println("path:" + path);
    assertTrue(path.endsWith(File.separator + "approvals" + File.separator));
  }
}
