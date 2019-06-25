package org.approvaltests.packagesettings.basedirectory;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.MessageFormat;

import org.approvaltests.Approvals;
import org.junit.Test;

public class TestApprovalDirectoryConfig
{
  @Test
  public void testApprovalBaseDirectory()
  {
    String path = Approvals.createApprovalNamer().getSourceFilePath();
    System.out.println("path:" + path);
    assertTrue(path.contains(MessageFormat.format("{0}test{0}resources{0}org{0}", File.separator)));
  }
}
