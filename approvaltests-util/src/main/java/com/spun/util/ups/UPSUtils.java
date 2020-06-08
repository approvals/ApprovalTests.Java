/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.ups;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.velocity.context.Context;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.spun.util.NumberUtils;
import com.spun.util.logger.SimpleLogger;
import com.spun.util.parser.MassAmount;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

/**
 * @author Llewellyn Falco
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 **/
public class UPSUtils
{
  static final String              UPS_URL        = "https://wwwcie.ups.com/ups.app/xml/Rate";
  private static String            HEADER         = "<?xml version=\"1.0\"?><AccessRequest xml:lang=\"en-US\"><AccessLicenseNumber>$config.getAccessLicenseNumber()</AccessLicenseNumber><UserId>$config.getUserId()</UserId><Password>$config.getPassword()</Password></AccessRequest>";
  private static String            REQUEST        = "<?xml version=\"1.0\"?><RatingServiceSelectionRequest xml:lang=\"en-US\"><Request><TransactionReference><CustomerContext>Rating and Service</CustomerContext><XpciVersion>1.0001</XpciVersion></TransactionReference><RequestAction>Rate</RequestAction><RequestOption>shop</RequestOption></Request><PickupType><Code>01</Code></PickupType><Shipment><Shipper><Address><PostalCode>$shipment.getMainPackage().getOriginatingZipCode()</PostalCode></Address></Shipper><ShipTo><Address><PostalCode>$shipment.getMainPackage().getToZipCode()</PostalCode><CountryCode>$shipment.getMainPackage().getToCountryCode()</CountryCode>#if ($shipment.getMainPackage().isResidential())<ResidentialAddressIndicator/>#end</Address></ShipTo><Service><Code>11</Code></Service>#foreach ($package in $shipment.getPackages())<Package><PackagingType><Code>02</Code><Description>Package</Description></PackagingType><Description>Rate Shopping</Description><PackageWeight><Weight>$package.getPackageWeightInPounds()</Weight></PackageWeight>#if ($package.getPackageLength() != 0)<Dimensions><UnitOfMeasurement><Code>IN</Code></UnitOfMeasurement><Length>$package.getPackageLength()</Length><Width>$package.getPackageWidth()</Width><Height>$package.getPackageHeight()</Height></Dimensions>#end</Package>#end<ShipmentServiceOptions/></Shipment></RatingServiceSelectionRequest>";
  private static UPSQuoteRetriever quoteRetriever = new UPSQuoteRetriever();
  
  public static void setUPSQuoteRetriever(UPSQuoteRetriever quoteRetriever)
  {
    UPSUtils.quoteRetriever = quoteRetriever;
  }
  
  public static UPSQuote[] getQuote(UPSConfig config, UPSPackage package1)
      throws SAXException, IOException, ParserConfigurationException, FactoryConfigurationError
  {
    return getQuote(config, new UPSPackage[]{package1});
  }
  
  public static UPSQuote[] getQuote(UPSConfig config, UPSPackage packages[])
      throws SAXException, ParserConfigurationException, FactoryConfigurationError, HttpException, IOException
  {
    packages = createAcceptablePackages(packages);
    String reqbody = constructRequestBody(config, packages);
    return getQuote(config, reqbody);
  }
  
  public static UPSQuote[] getQuote(UPSConfig config, String reqbody)
      throws SAXException, ParserConfigurationException, FactoryConfigurationError, HttpException, IOException
  {
    HttpClient client = new HttpClient();
    PostMethod post = new PostMethod(UPS_URL);
    post.setRequestBody(reqbody);
    InputStream response = quoteRetriever.getResponse(client, post);
    UPSQuote[] quotes = extractQuotes(response);
    return quotes;
  }
  
  private static UPSPackage[] createAcceptablePackages(UPSPackage[] packages)
  {
    ArrayList<UPSPackage> list = new ArrayList<UPSPackage>();
    for (int i = 0; i < packages.length; i++)
    {
      UPSPackage pack = packages[i];
      if (pack.getPackageWeightInPounds() < 1)
      {
        pack = new UPSPackage(pack.getOriginatingZipCode(), pack.getToZipCode(), pack.getToCountryCode(), 1,
            MassAmount.POUNDS, pack.getPackageLength(), pack.getPackageWidth(), pack.getPackageHeight(),
            pack.isResidential());
      }
      if (pack.getPackageWeightInPounds() > 150)
      {
        double weight = pack.getPackageWeightInPounds();
        while (weight > 150)
        {
          UPSPackage newPack = new UPSPackage(pack.getOriginatingZipCode(), pack.getToZipCode(),
              pack.getToCountryCode(), 150, MassAmount.POUNDS, pack.getPackageLength(), pack.getPackageWidth(),
              pack.getPackageHeight(), pack.isResidential());
          list.add(newPack);
          weight -= 150;
        }
        pack = new UPSPackage(pack.getOriginatingZipCode(), pack.getToZipCode(), pack.getToCountryCode(), weight,
            MassAmount.POUNDS, pack.getPackageLength(), pack.getPackageWidth(), pack.getPackageHeight(),
            pack.isResidential());
      }
      list.add(pack);
    }
    return UPSPackage.toArray(list);
  }
  
  private static UPSQuote[] extractQuotes(InputStream response)
      throws SAXException, IOException, ParserConfigurationException, FactoryConfigurationError
  {
    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(response);
    ArrayList<UPSQuote> quotes = new ArrayList<UPSQuote>();
    NodeList list = document.getDocumentElement().getElementsByTagName("RatedShipment");
    for (int i = 0; i < list.getLength(); i++)
    {
      quotes.add(extractQuote(list.item(i)));
    }
    if (quotes.size() == 0)
    {
      SimpleLogger.warning("Couldn't find quote in response " + getDocument(document));
    }
    return UPSQuote.toArray(quotes);
  }
  
  private static Node getDocument(Document document)
  {
    return document != null ? document.getChildNodes().item(0) : null;
  }
  
  private static UPSQuote extractQuote(Node node)
  {
    String service;
    double cost = 0.0;
    Node serviceNode = getNodeByName(node, "Service");
    Node code = getNodeByName(serviceNode, "Code");
    service = code.getFirstChild().getNodeValue();
    Node totalCharges = getNodeByName(node, "TotalCharges");
    Node monetaryValue = getNodeByName(totalCharges, "MonetaryValue");
    cost = NumberUtils.load(monetaryValue.getFirstChild().getNodeValue(), 0.0);
    return new UPSQuote(UPSServiceType.getForCode(service), cost);
  }
  
  private static Node getNodeByName(Node node, String childNode)
  {
    NodeList list = node.getChildNodes();
    for (int i = 0; i < list.getLength(); i++)
    {
      if (list.item(i).getNodeName().equals(childNode)) { return list.item(i); }
    }
    return null;
  }
  
  private static String constructRequestBody(UPSConfig config, UPSPackage packages[])
  {
    UPSRequest req = new UPSRequest(config, packages);
    String header = VelocityParser.parseString(HEADER, req);
    String main = VelocityParser.parseString(REQUEST, req);
    return header + main;
  }
  
  public static void main(String[] args)
  {
    SimpleLogger.variable(HEADER);
    SimpleLogger.variable(REQUEST);
  }
  
  public static class UPSRequest implements ContextAware
  {
    private UPSConfig   config;
    private UPSShipment shipment;
    public UPSRequest(UPSConfig config, UPSPackage packages[])
    {
      this.config = config;
      this.shipment = new UPSShipment(packages);
    }
    public void setupContext(Context context)
    {
      context.put("config", config);
      context.put("shipment", shipment);
    }
  }
}