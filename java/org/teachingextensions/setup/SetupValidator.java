package org.teachingextensions.setup;

import java.io.File;

import com.spun.util.io.FileUtils;

public class SetupValidator
{
  public enum SetupCheckPoints {
    MetadataUnzipped, EclipseIsInstalled
  }
  public static void main(String[] args)
  {
    SetupConfig config = new SetupConfig();
    validateEclipse(config);
    validateWorkspace(config);
    launchEclipse(config);
    config.assertSetupIsCorrect();
  }
  private static void launchEclipse(SetupConfig config)
  {
  }
  private static void validateWorkspace(SetupConfig config)
  {
    boolean codeExists = isCodeUnpacked();
    boolean workSpaceExists = isWorkSpaceConfigured(config);
    System.out.println("The Teaching Kids Programming WorkSpace is installed " + workSpaceExists);
  }
  private static boolean isWorkSpaceConfigured(SetupConfig config)
  {
    String metadataPath = config.workspacePath + File.separator + ".metadata";
    boolean metadata = new File(metadataPath).exists();
    if (metadata)
    {
      String configPath = metadataPath + File.separator
          + ".plugins\\org.eclipse.core.runtime\\.settings\\org.eclipse.ui.editors.prefs";
      String configText = FileUtils.readFileWithSuppressedExceptions(new File(configPath));
      metadata = configText.contains("lineNumberRuler=true");
      config.setup.set(SetupCheckPoints.MetadataUnzipped, metadata,
          "The metadata was not unzipped correctly, you need to delete: " + metadataPath);
    }
    return metadata;
  }
  private static boolean isCodeUnpacked()
  {
    return false;
  }
  private static void validateEclipse(SetupConfig config)
  {
    boolean exists = new File(config.eclipsePath).exists();
    config.setup.set(SetupCheckPoints.EclipseIsInstalled, exists, "could not find Eclipse at "
        + config.eclipsePath);
  }
}
