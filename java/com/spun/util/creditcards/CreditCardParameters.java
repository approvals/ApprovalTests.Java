package com.spun.util.creditcards;

import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Properties;

import com.spun.util.StringUtils;

public class CreditCardParameters
{
  public Properties          parameters      = new Properties();
  public static final String SALE            = "S";
  public static final String VOID            = "V";
  public static final String AUTHORIZATION   = "A";
  public static final String DELAYED_CAPTURE = "D";
  public static final String CREDIT          = "C";
  public static final String INQUIRY         = "I";
  /***********************************************************************/
  public CreditCardParameters()
  {
    addParameter("TENDER", "C");
  }
  /***********************************************************************/
  public CreditCardParameters(CreditCardConfiguration configuration)
  {
    this();
    setPassword(configuration.getPassword());
    setResellerId(configuration.getResellerId());
    setUserId(configuration.getUserId());
    setVendorId(configuration.getVenderId());
    
  }
  /***********************************************************************/
  public String getParameter(String key)
  {
    return parameters.getProperty(key);
  }
  /***********************************************************************/
  public void addParameter(String key, String value)
  {
    if ((key == null) || (value == null)) { return; }

    if ((value.indexOf('&') != -1) && (value.indexOf('=') != -1))
    {
      key = key + "[" + value.length() + "]";
    }
    parameters.setProperty(key, value);
  }
  /***********************************************************************/
  public String getParameterString()
  {
    StringBuffer param = new StringBuffer();
    String key = null;
    Enumeration keys = parameters.propertyNames();
    while (keys.hasMoreElements())
    {
      key = keys.nextElement().toString();
      param.append(key + "=" + parameters.getProperty(key) + "&");
    }
    param = param.deleteCharAt(param.length() - 1); //remove the last '&'
    return param.toString();
  }
  /***********************************************************************/
  public void setCreditCardNumber(String number)
  {
    addParameter("ACCT", number);
  }
  /***********************************************************************/
  /**
   * CSC code is the 3 digit number on the back of the card.
   */
  public void setCardSecurityCode(String csc)
  {
    
    addParameter("CVV2", csc);
  }
  /***********************************************************************/
  public void setAmount(double dollarAmount)
  {
    addParameter("AMT", getFormatedDouble(dollarAmount));
  }
  /***********************************************************************/
  public static String getFormatedDouble(double dollarAmount)
  {
    NumberFormat dFormat = NumberFormat.getNumberInstance();
    dFormat.setMaximumFractionDigits(2);
    dFormat.setMinimumFractionDigits(2);
    dFormat.setGroupingUsed(false);
    ((java.text.DecimalFormat) dFormat).setNegativePrefix("-");
    return dFormat.format(dollarAmount);
  }
  /***********************************************************************/
  /**
   * The Format must be MMYY
   **/
  public void setExpirationDate(String date)
  {
    addParameter("EXPDATE", date);
  }
  /***********************************************************************/
  public String getExpirationDate()
  {
    return getParameter("EXPDATE");
  }
  /***********************************************************************/
  public void setPaymentReferenceId(String paymentReferenceId)
  {
    addParameter("ORIGID", paymentReferenceId);
  }
  /***********************************************************************/
  public void setResellerId(String resellerId)
  {
    addParameter("PARTNER", resellerId);
  }
  /***********************************************************************/
  public void setPassword(String password)
  {
    addParameter("PWD", password);
  }
  /***********************************************************************/
  /** 
   * S - sell
   * C - Credit (refund)
   * A - Authorization
   * D - Delayed Capture
   * F - Voice Authorize
   * V - Void
   * I - Inquiry
   **/
  public void setTransactionType(String type)
  {
    addParameter("TRXTYPE", type);
  }
  /***********************************************************************/
  public String getTransactionType()
  {
    return getParameter("TRXTYPE");
  }
  /***********************************************************************/
  public void setUserId(String user)
  {
    addParameter("USER", user);
  }
  /***********************************************************************/
  public void setVendorId(String vendor)
  {
    addParameter("VENDOR", vendor);
  }
  /***********************************************************************/
  public void setSalesTax(double dollarAmount)
  {
    addParameter("TAXAMT", getFormatedDouble(dollarAmount));
  }
  /***********************************************************************/
  public void setOrderNumber(String orderNumber)
  {
    addParameter("PONUM", orderNumber);
  }
  /***********************************************************************/
  public void setAddress(String street)
  {
    if (street == null) { return; }
    if (street.length() > 30)
    {
      street = street.substring(0, 30);
    }
    addParameter("STREET", street);
  }
  /***********************************************************************/
  public void setZipCode(String zip)
  {
    if (zip == null) { return; }
    if (zip.length() > 9)
    {
      zip = zip.substring(0, 9);
    }
    zip = StringUtils.stripNonNumeric(zip);
    addParameter("ZIP", zip);
  }
  /***********************************************************************/
  /*public void setTransactionId(String transactionId)
  {
    if (transactionId != null) 
    {
      addParameter()
    }
  }*/
  /***********************************************************************/
  public String toString()
  {
    return parameters.toString();
  }
  /***********************************************************************/
  /***********************************************************************/
}