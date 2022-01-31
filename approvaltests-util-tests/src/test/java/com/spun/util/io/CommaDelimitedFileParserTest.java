package com.spun.util.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommaDelimitedFileParserTest
{
  /*
   * Test method for 'com.spun.util.io.CommaDelimitedFileParser.parse(File)'
   */
  @Test
  public void testParse() throws Exception
  {
    InputStream source = getClass().getResourceAsStream("Book1.csv");
    File targetFile = File.createTempFile("tmp", ".csv");
    targetFile.deleteOnExit();
    OutputStream target = new FileOutputStream(targetFile);
    FileUtils.copyStream(source, target);
    String[][] results = CommaDelimitedFileParser.parse(targetFile);
    assertEquals(2, results.length, "Number of records");
    String[] row1 = results[0];
    String[] row2 = results[1];
    assertEquals("bravo", row1[1], "Non-quoted record contents");
    assertEquals("charlie, delta", row1[2], "Quoted record contents");
    assertEquals("epsilon", row1[3], "Non-quoted record contents");
    assertEquals("Yes, I'am", row2[1], "spaces");
  }
  @Test
  public void testParseToMap() throws Exception
  {
    String data = "\"label1\",\"label2\"\n\"1x1\",\"1x2\"\n\"2x1\",\"2x2\"";
    Map<String, String>[] extractedData = CommaDelimitedFileParser.parseToMap(data);
    assertEquals(2, extractedData[0].keySet().size(), "columns");
    assertEquals(2, extractedData.length, "rows");
    assertTrue(extractedData[0].containsKey("label1"), "label1");
    assertTrue(extractedData[0].containsKey("label2"), "label2");
    assertTrue(extractedData[0].containsKey("label1"), "label1");
    assertTrue(extractedData[0].containsKey("label2"), "label2");
    assertEquals("1x1", extractedData[0].get("label1"));
    assertEquals("1x2", extractedData[0].get("label2"));
    assertEquals("2x1", extractedData[1].get("label1"));
    assertEquals("2x2", extractedData[1].get("label2"));
  }
}
