package org.approvaltests.writers;

import java.io.InputStream;

import com.spun.util.io.FileUtils;

public class ApprovalXmlWriter extends ApprovalTextWriter
{
  public ApprovalXmlWriter(String text)
  {
    super(text, "xml");
  }
  @Override
  public String writeReceivedFile(String received) throws Exception
  {
    received = super.writeReceivedFile(received);
    format(received);
    return received;
  }
  private void format(String fileName) throws Exception
  {
    String text = "\"C:\\temp\\xmlstarlet-1.0.1\\xml.exe\" format \"%s\"";
    text = String.format(text, fileName);
    Process exec = Runtime.getRuntime().exec(text);
    InputStream stream = exec.getInputStream();
    Thread.sleep(400);
    FileUtils.redirectInputToFile(fileName, stream);

  }
}
