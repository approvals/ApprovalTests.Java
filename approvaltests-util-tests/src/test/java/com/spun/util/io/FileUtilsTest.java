package com.spun.util.io;

import org.approvaltests.Approvals;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A static class of convenience functions for Files
 **/
public class FileUtilsTest
{
  @Test
  public void testDirectoryName() throws Exception
  {
    String name = "Set Enterprises Inc.";
    assertEquals("Set Enterprises Inc", FileUtils.getDirectoryFriendlyName(name));
    name = "Lucon Kids Inc.";
    assertEquals("Lucon Kids Inc", FileUtils.getDirectoryFriendlyName(name));
  }

  @Test
  public void testExtensionWithDot() throws Exception
  {
    assertEquals(".txt", FileUtils.getExtensionWithDot("c:\\some.thing\\there\\a.txt"));
  }

  @Test
  public void testExtensionWithoutDot() throws Exception
  {
    assertEquals("txt", FileUtils.getExtensionWithoutDot("c:\\some.thing\\there\\a.txt"));
  }

  @Test
  public void testIsNonEmptyFile() throws Exception
  {
    assertTrue(FileUtils.isNonEmptyFile(adjacentFile("FileTestUtils.java")));
  }

  @Test
  public void testIsDefaultText() throws Exception
  {
    String text = FileUtils.readFile(new File(adjacentFile("missing_file.txt")), "default text");
    assertEquals("default text", text);
  }

  private String adjacentFile(String name)
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    String file = namer.getSourceFilePath() + name;
    return file;
  }

  @Test
  public void testCopyFile() throws Exception
  {
    File first = File.createTempFile("unitTest", ".txt");
    File second = File.createTempFile("unitTestCopy", ".txt");
    first.deleteOnExit();
    second.deleteOnExit();
    try (BufferedWriter writer = Files.newBufferedWriter(first.toPath(), StandardCharsets.UTF_8))
    {
      writer.write("Mary had a little lamb");
    }
    FileUtils.copyFile(first, second);
    assertEquals(first.length(), (Object) second.length(), "File sizes ");
  }

  @UseReporter(ClipboardReporter.class)
  @Test
  public void testSaveToFile()
  {
    Reader input = new StringReader("hello Approvals");
    File file = FileUtils.saveToFile("pre_", input);
    Approvals.verify(file);
  }

  @Test
  public void testReadBuffer()
  {
    String input = """
        This is line 1
        This is line 2
        """;
    assertReadBuffer(input);
    assertReadBuffer(input.trim());
    input = """
        This is line 1

        This is line 2


        """;
    assertReadBuffer(input);
    assertReadBuffer(input.trim());
  }

  private static void assertReadBuffer(String input)
  {
    StringReader reader = new StringReader(input);
    BufferedReader bufferedReader = new BufferedReader(reader);
    String result = FileUtils.readBuffer(bufferedReader);
    assertEquals(input, result);
  }
}
