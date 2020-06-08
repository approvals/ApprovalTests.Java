/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.ups;

import java.util.ArrayList;

/**
 * @author Llewellyn Falco
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 **/
public class UPSQuote
{
  public enum Assessment {
    VALID, INVALID, NOT_UPS
  }
  
  private double price;
  private UPSServiceType serviceType;
  
  public UPSQuote(UPSServiceType serviceType, double price)
  {
    this.price = price;
    this.serviceType = serviceType;
  }
  
  private UPSQuote()
  {
  }
  
  public double getPrice()
  {
    return price;
  }
  
  public UPSServiceType getServiceType()
  {
    return serviceType;
  }
  
  public static UPSQuote add(UPSQuote quote1, UPSQuote quote2, int quote2Multiplier)
  {
    UPSQuote quote = new UPSQuote();
    quote.serviceType = (quote1 == null) ? quote2.getServiceType() : quote1.getServiceType();
    quote.price = (quote1 == null) ? 0 : quote1.getPrice();
    quote.price += quote2.price * quote2Multiplier;
    return quote;
  }
  /************************************************************************/
  public String toString()
  {
    String value = "com.spun.util.ups.UPSQuote[";
    value += " price = " + price + ",\n" + " serviceType = '" + serviceType + "'" + "]";
    return value;
  }
  
  public static UPSQuote[] toArray(ArrayList<? extends UPSQuote> found)
  {
    return (UPSQuote[]) found.toArray(new UPSQuote[found.size()]);
  }
  
  public static boolean isUPSShippingMethod(String method)
  {
    return UPSServiceType.getByFullName(method) != null;
  }
  
  public static Assessment assesValidUPSMethod(String method)
  {
    if (method == null || !method.startsWith("UPS")) { return Assessment.NOT_UPS; }
    return isUPSShippingMethod(method) ? Assessment.VALID : Assessment.INVALID;
  }
  
  
}
