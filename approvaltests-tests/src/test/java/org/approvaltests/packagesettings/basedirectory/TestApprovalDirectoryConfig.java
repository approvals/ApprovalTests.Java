package org.approvaltests.packagesettings.basedirectory;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
