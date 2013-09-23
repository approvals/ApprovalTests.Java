package com.spun.util.shipping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.spun.util.MySystem;
import com.spun.util.ObjectUtils;
import com.spun.util.ups.UPSPackage;

public class USPSDomesticRateCalculator
{
  public static final String SERVERNAME = "testing.shippingAPIs.com";
  public static final String USERID = "484ABAKA5143";

  /************************************************************************/
  public enum Container {
    FLAT_RATE_ENVELOPE("Flat Rate Envelope"), FLAT_RATE_BOX("Flat Rate Box");
    private final String label;

    Container(String label)
    {
      this.label = label;
    }

    public String toString()
    {
      return label;
    }
  }

  /************************************************************************/
  public enum Service {
    EXPRESS("Express"), FIRST_CLASS("First Class"), PRIORITY("Priority"), PARCEL(
        "Parcel"), BPM("BPM"), LIBRARY("Library"), MEDIA("Media"), ALL("All");
    private final String label;

    Service(String label)
    {
      this.label = label;
    }

    public String toString()
    {
      return label;
    }
  }

  /************************************************************************/
  public enum Size {
    REGULAR, LARGE, OVERSIZE
  }

  /************************************************************************/
  public Collection<Rate> getRates(Service service, String zipFrom,
      String zipTo, int pounds, int ounces, Container container, Size size,
      boolean machinable)
  {
    String uri = String
        .format(
            "<?xml version=\"1.0\" ?><RateV2Request USERID=\"%s\"><Package ID=\"0\"><Service>%s</Service><ZipOrigination>%s</ZipOrigination><ZipDestination>%s</ZipDestination><Pounds>%d</Pounds><Ounces>%d</Ounces><Container>%s</Container><Size>%s</Size><Machinable>%s</Machinable></Package></RateV2Request>",
            USERID, service, zipFrom, zipTo, pounds, ounces,
            container == null ? "" : container, size,
            machinable == true ? "TRUE" : "");
    MySystem.variable(uri);
    return sendRequest(uri);
  }

  /************************************************************************/
  private Collection<Rate> sendRequest(String uri)
  {
    try
    {
      uri = String.format("http://%s/ShippingAPITest.dll?API=RateV2&XML=%s",
          SERVERNAME, URLEncoder.encode(uri, "UTF-8"));
    } catch (UnsupportedEncodingException uee)
    {
      throw new RuntimeException("Unable to encode URL", uee);
    }
    HttpClient client = new HttpClient();
    HttpMethod method = null;
    method = new GetMethod(uri);
    String responseBody = null;
    try
    {
      client.executeMethod(method);
      responseBody = method.getResponseBodyAsString();
    } catch (HttpException he)
    {
      throw new RuntimeException("Http error connecting to '" + uri + "'", he);
    } catch (IOException ioe)
    {
      throw new RuntimeException("Unable to connect to '" + uri + "'", ioe);
    }
    MySystem.variable(responseBody);
    ResponseParser handler = new ResponseParser();
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser;
    try
    {
      saxParser = factory.newSAXParser();
      saxParser.parse(method.getResponseBodyAsStream(), handler);
    } catch (ParserConfigurationException e)
    {
      throw new RuntimeException("Sax parser error");
    } catch (SAXException e)
    {
      throw new RuntimeException("Sax parser error");
    } catch (IOException e)
    {
      throw new RuntimeException("Sax parser error");
    }
    return Collections.unmodifiableCollection(handler.getRates());
  }

  /************************************************************************/
  public Rate[] getRates(UPSPackage[] boxes)
  {
    String opening = "<?xml version=\"1.0\" ?><RateV2Request USERID=" + USERID
        + ">";
    String closing = "</RateV2Request>";
    String packages = "";
    for (int i = 0; i < boxes.length; i++)
    {
      Size size = getEquivalentSize(boxes[i]);
      double rawPounds = boxes[i].getPackageWeightInPounds();
      double pounds = Math.floor(rawPounds);
      double rawOunces = rawPounds - pounds;
      double ounces = Math.ceil(16.00 * rawOunces);
      String zipOrig = boxes[i].getOriginatingZipCode();
      String zipDest = boxes[i].getToZipCode();
      if (!ObjectUtils.isEqual(boxes[i].getToCountryCode(), "United States"))
      {
        throw new RuntimeException(
            "Not yet handling international shipments via USPS");
      }
      packages += String
          .format(
              "<Package ID=\"%d\"><Service>%s</Service><ZipOrigination>%s</ZipOrigination><ZipDestination>%s</ZipDestination><Pounds>%d</Pounds><Ounces>%d</Ounces><Size>%s</Size></Package>",
              i, Service.ALL, zipOrig, zipDest, pounds, ounces, size);
    }
    Collection<Rate> rates = sendRequest(opening + packages + closing);
    return Rate.toArray(rates);
  }

  /************************************************************************/
  private static Size getEquivalentSize(UPSPackage box)
  {
    int size = box.getPackageLength() + 2
        * (box.getPackageWidth() + box.getPackageHeight());
    if (size < 84)
    {
      return Size.REGULAR;
    } else if (84 < size && size < 108)
    {
      return Size.LARGE;
    } else if (108 < size && size < 130)
    {
      return Size.OVERSIZE;
    } else
    {
      // return something to indicate not valid for USPS
    }
    return null;
  }

  /************************************************************************/
  /************* INNER CLASSES *************/
  /************************************************************************/
  public class ResponseParser extends DefaultHandler
  {
    private Collection<Rate> rates = new ArrayList<Rate>();
    private String mailService;
    private double rate;
    private boolean isMailService;
    private boolean isRate;

    public Collection<Rate> getRates()
    {
      return rates;
    }

    public void startElement(String uri, String localName, String qName,
        Attributes attributes) throws SAXException
    {
      if (qName.equals("MailService"))
      {
        isMailService = true;
      } else if (qName.equals("Rate"))
      {
        isRate = true;
      } else if (qName.equals("Postage"))
      {
        mailService = null;
        rate = 0;
        isMailService = false;
        isRate = false;
      }
    }

    /************************************************************************/
    public void characters(char buf[], int offset, int len) throws SAXException
    {
      String s = new String(buf, offset, len);
      if (isMailService)
        mailService = s;
      else if (isRate)
        rate = Double.valueOf(s);
    }

    /************************************************************************/
    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
      if (qName.equals("MailService"))
      {
        isMailService = false;
      } else if (qName.equals("Rate"))
      {
        isRate = false;
      } else if (qName.equals("Postage"))
      {
        rates.add(new Rate(mailService, rate));
      }
    }
  }
  /************************************************************************/
  /************************************************************************/
}
