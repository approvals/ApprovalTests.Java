package org.teachingextensions.setup;

import java.io.File;

import com.spun.util.io.FileUtils;
import com.spun.util.servlets.ValidationError;

public class SetupValidator
{
  public enum SetupCheckPoints {
    MetadataUnzipped
  }
  public static void main(String[] args)
  {
    ValidationError setup = new ValidationError(SetupCheckPoints.values());
    validateEclipse();
    validateWorkspace(setup);
    if (!setup.isOk()) { throw setup; }
  }
  private static void validateWorkspace(ValidationError setup)
  {
    boolean codeExists = isCodeUnpacked();
    String path = "C:\\Users\\Llewellyn\\workspace\\ApprovalTestsKoans\\TeachingKidsProgramming.Java";
    boolean workSpaceExists = isWorkSpaceConfigured(path, setup);
    System.out.println("The WorkSpace is installed " + workSpaceExists);
  }
  private static boolean isWorkSpaceConfigured(String path, ValidationError setup)
  {
    String metadataPath = path + File.separator + ".metadata";
    boolean metadata = new File(metadataPath).exists();
    if (metadata)
    {
      String configPath = metadataPath + File.separator
          + ".plugins\\org.eclipse.core.runtime\\.settings\\org.eclipse.ui.editors.prefs";
      String config = FileUtils.readFileWithSuppressedExceptions(new File(configPath));
      metadata = config.contains("lineNumberRuler=true");
      setup.set(SetupCheckPoints.MetadataUnzipped, metadata,
          "The metadata was not unzipped correctly, you need to delete: " + metadataPath);
    }
    return metadata;
  }
  private static boolean isCodeUnpacked()
  {
    return false;
  }
  private static void validateEclipse()
  {
    //check that the eclipse.exe exists
    boolean exists = isEclipseInstalled();
    System.out.println("Eclipse is installed " + exists);
  }
  private static boolean isEclipseInstalled()
  {
    File f = new File("c:\\eclipse\\eclipse.exe");
    return f.exists();
  }
}
