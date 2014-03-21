package org.teachingextensions.setup;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import com.spun.util.ObjectUtils;
import com.spun.util.io.ZipUtils;

public class MacThumbDriveInstaller
{
  public void install() throws Exception
  {
    installEclipse();
    installWorkspace();
    launch();
  }
  private void launch()
  {
    String command = "java -jar TKP_Launcher.jar";
    try
    {
      Process exec = Runtime.getRuntime().exec(command, null,
          new File(getDesktop().getAbsolutePath() + "/TeachingKidsProgramming.Java-master"));
    }
    catch (IOException e)
    {
      ObjectUtils.throwAsError(e);
    }
  }
  private void installWorkspace() throws IOException
  {
    File zipFile = new File("./TeachingKidsProgramming.Java-master.zip");
    File unzipTo = getDesktop();
    System.out.println("Unzip to " + unzipTo);
    ZipUtils.doUnzip(unzipTo, zipFile);
  }
  public File getDesktop()
  {
    File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
    File unzipTo = new File(homeDirectory.getAbsolutePath() + "/Desktop");
    return unzipTo;
  }
  private void installEclipse() throws Exception
  {
    File installDir = new File("/Applications/Eclipse");
    if (installDir.exists()) { return; }
    ZipUtils.doUnzip(new File("/Applications"), new File("./eclipse-mac.zip"));
  }
}
