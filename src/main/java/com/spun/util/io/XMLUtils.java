package com.spun.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.spun.util.MySystem;
import com.spun.util.StringUtils;


public class XMLUtils
{
  /***********************************************************************/
  public static String locateFile(String fileLocation, String backupPaths[]) throws Exception
  {
    String[] newArray = new String[backupPaths.length+1];
    System.arraycopy(backupPaths, 0, newArray, 1, backupPaths.length);
    newArray[0] = ".";
    backupPaths = newArray;
    for (int i = 0; i < backupPaths.length; i++)
    {
      String tfileLocation = backupPaths[i] + File.separator + fileLocation;
      File file = new File(tfileLocation);
      if (file.exists())
      {
        return file.getAbsolutePath();
      }
    }
    throw new Error(String.format("Couldn't find '%s' from locations %s with current directory '%s'" ,fileLocation ,Arrays.asList(backupPaths),new File(".").getAbsolutePath()));
  }
  /***********************************************************************/
  public static HashMap<String, Object> parseProperties(String absoluteFileLocation, XMLNodeExtractor extractor) throws Exception
  {
    try
    {
      FileInputStream stream = new FileInputStream(absoluteFileLocation);
      Document document = parseXML(stream);
      return extractProperties(document, extractor);
    }
    catch (Exception e)
    {
      MySystem.variable("Property File ", absoluteFileLocation);
      throw e;
    }
  }
  /***********************************************************************/

  private static HashMap<String, Object> extractProperties(Document document, XMLNodeExtractor extractor)
  {
    HashMap<String, Object> properties = new HashMap<String, Object>();
    NodeList list = document.getDocumentElement().getChildNodes();
    for (int i = 0; i < list.getLength(); i++)
    {
      extractor.extractProperty(list.item(i), properties);
    }
    return properties;
  }
  /***********************************************************************/
  
  public static Document parseXML(String xml) throws Exception
  {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    return builder.parse(new InputSource(new StringReader(xml)));
  }

  /***********************************************************************/
  public static Document parseXML(File xml) throws Exception
  {
    return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(xml));
  }
  /***********************************************************************/
  
  public static Document parseXML(InputStream stream) throws Exception
  {
    return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
  }
  /***********************************************************************/
  
  public static String extractStringValue(Node node)
  {
    NodeList childNodes = node.getChildNodes();
    if ((childNodes.getLength() == 1) && (childNodes.item(0).getChildNodes().getLength() == 0))
    {
      return StringUtils.loadNullableString(childNodes.item(0).getNodeValue());
    }
    else if (childNodes.getLength() > 1)
    {
      throw new Error("Should not be multiple children for node '" + node.getNodeName()+ "'");
    }
    return null;
  }
  /**********************************************************************/
  /************************************************************************/
  
  
}