package com.spun.util.io;

import com.spun.util.io.xml.XmlExtractorUtil;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XMLUtilsTest
{
  @Test
  void testXML() throws Exception
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

  @Test
  void testPrettyPrint()
  {
    var expected = """
        <?xml version="1.0" encoding="UTF-8"?><root>
          <child>value</child>
          <nested>
            <deep>content</deep>
          </nested>
        </root>
        """;
    String compactXml = "<root><child>value</child><nested><deep>content</deep></nested></root>";
    String result = XMLUtils.prettyPrint(compactXml, 2);
    Approvals.verify(result, new Options().inline(expected));
  }

  @Test
  void testPrettyPrintWithOrdering()
  {
    var expected = """
        <?xml version="1.0" encoding="UTF-8" standalone="no"?>
        <root>
            <age>30</age>
            <city>New York</city>
            <country>USA</country>
            <department>Engineering</department>
            <email>john.doe@example.com</email>
            <hobbies>
                <hobby>Reading</hobby>
                <hobby>Swimming</hobby>
                <hobby>Coding</hobby>
                <hobby>Gaming</hobby>
            </hobbies>
            <isActive>true</isActive>
            <name>John Doe</name>
            <phone>555-1234</phone>
            <salary>75000.50</salary>
        </root>
        """;
    String compactXml = """
          <root><name>John Doe</name><age>30</age><city>New York</city><isActive>true</isActive><salary>75000.50</salary><department>Engineering</department><hobbies><hobby>Reading</hobby><hobby>Swimming</hobby><hobby>Coding</hobby><hobby>Gaming</hobby></hobbies><email>john.doe@example.com</email><phone>555-1234</phone><country>USA</country></root>
        """;
    String result = XMLUtils.prettyPrint(compactXml, 4, true);
    Approvals.verify(result, new Options().inline(expected));
  }
}