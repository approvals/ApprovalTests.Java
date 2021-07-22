package com.spun.util.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spun.util.NumberUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.jupiter.api.Test;

public class NetUtilsTest
{
  @Test
  public void testSftp() throws Exception
  {
    File f = createFile();
    Properties p = new Properties();
    InputStream propertiesFile = getClass().getResourceAsStream("sftp.properties");
    if (propertiesFile == null)
    {
      // skip if no properties file
      return;
    }
    p.load(propertiesFile);
    String server = p.getProperty("server");
    String username = p.getProperty("username");
    String password = p.getProperty("password");
    FTPConfig config = new FTPConfig(server, 22, username, password);
    String remoteString = p.getProperty("remoteFilename");
    SshUtils.sftpUpload(config, f, remoteString);
    File f2 = SshUtils.sftpDownload(config, createTempFile(), remoteString);
    assertEquals(FileUtils.readFile(f), FileUtils.readFile(f2));
  }
  private File createFile() throws IOException
  {
    File file = createTempFile();
    FileUtils.writeFile(file, "Unexpected Text" + NumberUtils.getNumberOfDigits(3));
    return file;
  }
  private File createTempFile() throws IOException
  {
    File file = File.createTempFile("Test", ".txt");
    file.deleteOnExit();
    return file;
  }
}
