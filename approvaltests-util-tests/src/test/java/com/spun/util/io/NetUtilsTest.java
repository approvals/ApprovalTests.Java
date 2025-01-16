package com.spun.util.io;

import com.spun.util.NumberUtils;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
  @Test
  void testReadWebPageReturnsPageContent()
  {
    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse().setBody("hello, world!"));
    String s = NetUtils.readWebpage(server.url("/").toString());
    Approvals.verify(s);
  }
  @Test
  void testLoadWebPageWithQueryParams() throws InterruptedException
  {
    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse().setBody("hello, world!"));
    NetUtils.loadWebPage(server.url("/api").toString(), "query=param");
    RecordedRequest recordedRequest = server.takeRequest();
    assertEquals("/api?query=param", recordedRequest.getPath());
  }
  @Test
  void testReadWebPageWithoutQueryParams() throws InterruptedException
  {
    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse().setBody("hello, world!"));
    NetUtils.readWebpage(server.url("/api").toString());
    RecordedRequest recordedRequest = server.takeRequest();
    assertEquals("/api", recordedRequest.getPath());
  }
}
