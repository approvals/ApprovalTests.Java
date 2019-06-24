package org.approvaltests.packagesettings;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.approvaltests.Approvals;
import org.junit.Test;

public class TestApprovalSubdirectorys
{
  @Test
  public void testApprovalSubdirectory()
  {
    String path = Approvals.createApprovalNamer().getSourceFilePath();
    System.out.println("path:" + path);
    assertTrue(path.endsWith(File.separator + "approvals" + File.separator));
  }
}
