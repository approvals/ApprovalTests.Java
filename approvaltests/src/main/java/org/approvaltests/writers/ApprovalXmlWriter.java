package org.approvaltests.writers;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ApprovalXmlWriter extends ApprovalTextWriter
{
    public ApprovalXmlWriter(String text)
    {
        super(prettyPrint(text, 2), "xml");
    }

    public static String prettyPrint(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (TransformerException e) {
            return input;
        }
    }
}
