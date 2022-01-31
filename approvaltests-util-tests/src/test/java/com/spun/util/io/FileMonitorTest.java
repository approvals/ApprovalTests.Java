package com.spun.util.io;

import com.spun.util.io.FileMonitor.FileListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileMonitorTest
{
  FileMonitor monitor;
  File        root;
  boolean     done;
  @BeforeEach
  protected void setUp() throws Exception
  {
    File filea = File.createTempFile("tmp", "tmp");
    root = new File(filea.getParent() + File.separator + "fileMonitorRoot");
    root.mkdir();
    filea.delete();
    monitor = new FileMonitor(100);
    monitor.addFile(root);
    monitor.addListener(this.new TestListener());
    Thread.sleep(1000);
  }
  @AfterEach
  protected void tearDown() throws Exception
  {
    monitor.stop();
    root.delete();
  }
  @Test
  public void testNewFileDetected() throws Exception
  {
    done = false;
    File targetFile = File.createTempFile("tmp", ".dbf", root);
    for (int i = 0; i < 100 && !done; i++)
    {
      Thread.sleep(1000);
    }
    try
    {
      assertTrue(done, "File processed");
    }
    finally
    {
      targetFile.delete();
    }
  }
  private class TestListener implements FileListener
  {
    public void fileChanged(File file)
    {
      done = true;
    }
  }
}
