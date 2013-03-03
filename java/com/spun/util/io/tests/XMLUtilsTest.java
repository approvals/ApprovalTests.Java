package com.spun.util.io.tests;

import junit.framework.TestCase;
import org.w3c.dom.Document;
import com.spun.util.io.XMLUtils;
import com.spun.util.io.xml.XmlExtractorUtil;

public class XMLUtilsTest extends TestCase
{
  public void testXML() throws Exception
  {
   // String xml = "<?xml version=\"1.0\" ?><QBXML><QBXMLMsgsRs><ItemQueryRs requestID=\"2\" statusCode=\"0\" statusSeverity=\"Info\" statusMessage=\"Status OK\">1</ItemQueryRs></QBXMLMsgsRs></QBXML>";
    String xml = "<?xml version=\"1.0\" ?>\r\n" + 
            "<QBXML>\r\n" + 
            "<QBXMLMsgsRs>\r\n" + 
            "<HostQueryRs statusCode=\"0\" statusSeverity=\"Info\" statusMessage=\"Status OK\">\r\n" + 
            "<HostRet>\r\n" + 
            "<ProductName>superpro</ProductName>\r\n" + 
            "<MajorVersion>16</MajorVersion>\r\n" + 
            "<MinorVersion>0</MinorVersion>\r\n" + 
            "<SupportedQBXMLVersion>1.0</SupportedQBXMLVersion>\r\n" + 
            "<SupportedQBXMLVersion>1.1</SupportedQBXMLVersion>\r\n" + 
            "<SupportedQBXMLVersion>2.0</SupportedQBXMLVersion>\r\n" + 
            "<SupportedQBXMLVersion>2.1</SupportedQBXMLVersion>\r\n" + 
            "<SupportedQBXMLVersion>3.0</SupportedQBXMLVersion>\r\n" + 
            "<SupportedQBXMLVersion>4.0</SupportedQBXMLVersion>\r\n" + 
            "<SupportedQBXMLVersion>4.1</SupportedQBXMLVersion>\r\n" + 
            "<SupportedQBXMLVersion>5.0</SupportedQBXMLVersion>\r\n" + 
            "</HostRet>\r\n" + 
            "</HostQueryRs>\r\n" + 
            "</QBXMLMsgsRs>\r\n" + 
            "</QBXML>\r\n" + 
            "";
    Document document = XMLUtils.parseXML(xml);
    assertEquals(true, document.hasChildNodes());
//    assertNotNull(XmlExtractorUtil.traverseToTag("ItemQueryRs", document));
    assertNotNull(XmlExtractorUtil.traverseToTag("HostRet", document));
  }
}
