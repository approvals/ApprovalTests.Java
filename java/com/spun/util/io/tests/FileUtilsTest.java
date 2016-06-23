package com.spun.util.io.tests;

import java.io.File;
import java.io.FileWriter;

import com.spun.util.io.FileUtils;

import junit.framework.TestCase;

/**
 * A static class of convenience functions for Files
 **/
public class FileUtilsTest extends TestCase
{
  public void testDirectoryName() throws Exception
  {
    String name = "Set Enterprises Inc.";
    assertEquals("Set Enterprises Inc", FileUtils.getDirectoryFriendlyName(name));
    name = "Lucon Kids Inc.";
    assertEquals("Lucon Kids Inc", FileUtils.getDirectoryFriendlyName(name));
  }
  public void testExtensionWithDot() throws Exception
  {
    assertEquals(".txt", FileUtils.getExtensionWithDot("c:\\some.thing\\there\\a.txt"));
  }
  public void testExtensionWithoutDot() throws Exception
  {
    assertEquals("txt", FileUtils.getExtensionWithoutDot("c:\\some.thing\\there\\a.txt"));
  }
  /***********************************************************************/
  public void testCopyFile() throws Exception
  {
    File first = File.createTempFile("unitTest", ".txt");
    File second = File.createTempFile("unitTestCopy", ".txt");
    first.deleteOnExit();
    second.deleteOnExit();
    FileWriter writer = new FileWriter(first);
    writer.write("Mary had a little lamb");
    writer.close();
    FileUtils.copyFile(first, second);
    assertEquals("File sizes ", first.length(), second.length());
  }
  /************************************************************************/
  /************************************************************************/
}