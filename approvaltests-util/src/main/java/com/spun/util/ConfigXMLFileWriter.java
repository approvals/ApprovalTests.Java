package com.spun.util;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.lambda.functions.Function1;
import org.lambda.query.Query;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A static class of convenience functions for database access
 **/
public class ConfigXMLFileWriter
{
  public static void writeToFile(Class<?> clazz, String fileName, String exclude[]) throws Exception
  {
    Document domDocument = createDocument();
    Field fields[] = getFields(clazz, exclude);
    for (int i = 0; i < fields.length; i++)
    {
      addLine(fields[i], domDocument);
    }
    writeToIndentedXMLFile(fileName, domDocument);
  }
  private static void addLine(Field field, Document domDocument)
  {
    Element elem = domDocument.createElement(field.getName());
    if (field.getType().isArray())
    {
      Element inner = domDocument.createElement("VALUE");
      inner.appendChild(domDocument.createTextNode(" "));
      elem.appendChild(inner);
      inner = domDocument.createElement("VALUE");
      inner.appendChild(domDocument.createTextNode(" "));
      elem.appendChild(inner);
    }
    else
    {
      elem.appendChild(domDocument.createTextNode(" "));
    }
    domDocument.getDocumentElement().appendChild(elem);
  }
  public static Field[] getFields(Class<?> clazz, String... exclude)
  {
    Field fields[] = clazz.getFields();
    final List<String> excludeNames = Arrays.asList(exclude);
    Function1<Field, Boolean> selector = (Field a) -> (ClassUtils.IsPublicStatic(a)
        && ClassUtils.isPrimitiveField(a) && !excludeNames.contains(a.getName()));
    fields = Query.where(fields, selector).toArray(new Field[0]);
    return Query.orderBy(fields, a -> (a.getName()));
  }
  private static Document createDocument() throws Exception
  {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(false);
    documentBuilderFactory.setIgnoringElementContentWhitespace(true);
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    return documentBuilder.getDOMImplementation().createDocument("", "XML", null);
  }
  public static void writeToIndentedXMLFile(String configFile, Document domDocument) throws Exception
  {
    DataOutputStream out = new DataOutputStream(new FileOutputStream(configFile));
    TransformerFactory tFactory = TransformerFactory.newInstance();
    Transformer transformer = tFactory.newTransformer();
    DOMSource source = new DOMSource(domDocument);
    StreamResult result = new StreamResult(out);
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    transformer.transform(source, result);
    out.close();
  }
}