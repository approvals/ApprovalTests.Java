package org.approvaltests.writers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.scrubbers.RegExScrubber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator.Feature.WRITE_XML_DECLARATION;

public class ApprovalXmlWriterTest
{
  @Test
  public void plainXml()
  {
    Approvals.verifyXml("<xml><hello/><start>hi</start></xml>");
  }
  @Test
  public void xmlWithAttributes()
  {
    Approvals.verifyXml("<xml b=\"123\" a=\"456\"><hello x=\"y\"/><start>hi</start></xml>");
  }
  @Test
  public void xmlWithDeepAttributes()
  {
    Approvals
        .verifyXml("<xml b=\"1\" a=\"1\"><branch1 b=\"1\" a=\"1\"/><branch2 b=\"1\" a=\"1\">hi</branch2></xml>");
  }
  @Test
  public void xmlWithDeepAttributesWithScrubber()
  {
    Approvals.verifyXml(
        "<xml b=\"1\" a=\"1\"><branch1 b=\"1\" a=\"1\"/><branch2 b=\"1\" a=\"1\">hi</branch2></xml>",
        new Options(new RegExScrubber("hi", "hello")));
  }
  @Test
  public void invalidXml()
  {
    Approvals.verifyXml("<xml><hello/><start>hi</xml>");
    System.err.println("Note: The previous xml error (</start>) is expected ");
  }
  @Test
  void xmlOutputByJacksonIsHandledCorrectly() throws JsonProcessingException
  {
    ObjectWriter objectWriter = new XmlMapper().configure(WRITE_XML_DECLARATION, true).writer();
    String jacksonXml = objectWriter.writeValueAsString(new JacksonTestPOJO());
    String approvalsWriterXml = ApprovalXmlWriter.prettyPrint(jacksonXml, 2);
    Assertions.assertEquals(jacksonXml, approvalsWriterXml);
  }
  @Test
  void xmlOutputByJacksonIsHandledCorrectlyWhenPrettyPrintingIsEnabled() throws JsonProcessingException
  {
    ObjectWriter objectWriter = new XmlMapper().configure(WRITE_XML_DECLARATION, true)
        .writerWithDefaultPrettyPrinter();
    String jacksonXml = objectWriter.writeValueAsString(new JacksonTestPOJO());
    String approvalsWriterXml = ApprovalXmlWriter.prettyPrint(jacksonXml, 2);
    Assertions.assertEquals(jacksonXml, approvalsWriterXml);
  }
  private static class JacksonTestPOJO
  {
    public String getSomeText()
    {
      return "Some text";
    }
    public String getSomeTextWithAmpersand()
    {
      return "Some more text & some more";
    }
    public String getEmoji()
    {
      return "😸";
    }
  }
}
