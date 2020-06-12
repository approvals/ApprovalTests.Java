package com.spun.util.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import junit.framework.TestCase;
import com.spun.util.io.CommaDelimitedFileParser;
import com.spun.util.io.FileUtils;

public class CommaDelimitedFileParserTest extends TestCase {

	/*
	 * Test method for 'com.spun.util.io.CommaDelimitedFileParser.parse(File)'
	 */
	public void testParse() throws Exception {
    InputStream source = getClass().getResourceAsStream("Book1.csv");
    File targetFile = File.createTempFile("tmp", ".csv");
    targetFile.deleteOnExit();
    OutputStream target = new FileOutputStream(targetFile);
    FileUtils.copyStream(source, target); 
    
		String[][] results = CommaDelimitedFileParser.parse(targetFile);
		assertEquals("Number of records", 2, results.length);
		String[] row1 = results[0];
        String[] row2 = results[1];
		assertEquals("Non-quoted record contents", "bravo", row1[1]);
		assertEquals("Quoted record contents", "charlie, delta", row1[2]);
		assertEquals("Non-quoted record contents", "epsilon", row1[3]);
        assertEquals("spaces", "Yes, I'am", row2[1]);
	}
    
    public void testParseToMap() throws Exception {
      String data = "\"label1\",\"label2\"\n\"1x1\",\"1x2\"\n\"2x1\",\"2x2\"";
      
      Map<String,String>[] extractedData = CommaDelimitedFileParser.parseToMap(data);
      assertEquals("columns", 2, extractedData[0].keySet().size());
      assertEquals("rows", 2, extractedData.length);
      assertTrue("label1", extractedData[0].containsKey("label1"));
      assertTrue("label2", extractedData[0].containsKey("label2"));
      assertTrue("label1", extractedData[0].containsKey("label1"));
      assertTrue("label2", extractedData[0].containsKey("label2"));
      assertEquals("1x1", extractedData[0].get("label1") );
          assertEquals("1x2", extractedData[0].get("label2") );
          assertEquals("2x1", extractedData[1].get("label1") );
          assertEquals("2x2", extractedData[1].get("label2") );
    //      int r=1;
    //      for (Map record : extractedData )
    //      {
    //        assertEquals("Number of fields", 2, record.keySet().size());
    //        int c = 1;
    //        for (Map.Entry<String, String> column : record )
    //        {
    //          assertEquals("Label "+c, "label"+c, record.keySet()
    //          assertEquals(r+"x"+c, r+"x"+c, "");
    //        }
    //        r++;
    //      }
      
      
      
    }

}
