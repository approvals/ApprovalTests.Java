package com.spun.util.io;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spun.util.io.xml.XmlExtractorUtil;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

public class XMLUtilsTest
{
  @Test
  public void testXML() throws Exception
  {
    // String xml = "<?xml version=\"1.0\" ?><QBXML><QBXMLMsgsRs><ItemQueryRs requestID=\"2\" statusCode=\"0\" statusSeverity=\"Info\" statusMessage=\"Status OK\">1</ItemQueryRs></QBXMLMsgsRs></QBXML>";
    String xml = "<?xml version=\"1.0\" ?>\n" + "<QBXML>\n" + "<QBXMLMsgsRs>\n"
        + "<HostQueryRs statusCode=\"0\" statusSeverity=\"Info\" statusMessage=\"Status OK\">\n" + "<HostRet>\n"
        + "<ProductName>superpro</ProductName>\n" + "<MajorVersion>16</MajorVersion>\n"
        + "<MinorVersion>0</MinorVersion>\n" + "<SupportedQBXMLVersion>1.0</SupportedQBXMLVersion>\n"
        + "<SupportedQBXMLVersion>1.1</SupportedQBXMLVersion>\n"
        + "<SupportedQBXMLVersion>2.0</SupportedQBXMLVersion>\n"
        + "<SupportedQBXMLVersion>2.1</SupportedQBXMLVersion>\n"
        + "<SupportedQBXMLVersion>3.0</SupportedQBXMLVersion>\n"
        + "<SupportedQBXMLVersion>4.0</SupportedQBXMLVersion>\n"
        + "<SupportedQBXMLVersion>4.1</SupportedQBXMLVersion>\n"
        + "<SupportedQBXMLVersion>5.0</SupportedQBXMLVersion>\n" + "</HostRet>\n" + "</HostQueryRs>\n"
        + "</QBXMLMsgsRs>\n" + "</QBXML>\n" + "";
    Document document = XMLUtils.parseXML(xml);
    assertTrue(document.hasChildNodes());
    assertNotNull(XmlExtractorUtil.traverseToTag("HostRet", document));
  }
}
