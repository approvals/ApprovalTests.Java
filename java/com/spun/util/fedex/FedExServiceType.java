/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.fedex;

import org.lambda.query.Query;

import com.spun.util.ObjectUtils;

public enum FedExServiceType {
                              FedEx_Priority_Overnight("01"), FedEx_Standard_Overnight("05"), FedEx_2_Day("03"),
                              FedEx_First_Overnight("06"), FedEx_Express_Saver("20"), FedEx_Ground("92"),
                              FedEx_Ground_Home_Delivery("90"), FedEx_1_Day_Freight("70"),
                              FedEx_2_Day_Freight("80"), FedEx_3_Day_Freight("83"),
                              FedEx_International_Priority_Overnight("01"),
                              FedEx_International_First_Overnight("06"), FedEx_International_Economy("03"),
                              FedEx_International_Priority_Freight("70"),
                              FedEx_International_Enconomy_Freight("86"),;
  private String fullName;
  private String serviceCode;
  /***********************************************************************/
  private FedExServiceType(String serviceCode)
  {
    this.serviceCode = serviceCode;
    this.fullName = this.toString().replace("_", " ");
  }
  /***********************************************************************/
  @Override
  public String toString()
  {
    return fullName == null ? super.toString() : fullName;
  }
  /***********************************************************************/
  public boolean isInternational()
  {
    return fullName.startsWith("FedEx International");
  }
  /***********************************************************************/
  public static FedExServiceType[] getInternationalCodes()
  {
    return getCodes(true);
  }
  /***********************************************************************/
  private static FedExServiceType[] getCodes(final boolean international)
  {
    return Query.where(values(), a -> (a.isInternational() == international)).toArray(new FedExServiceType[0]);
  }
  /***********************************************************************/
  public static FedExServiceType[] getNationalCodes()
  {
    return getCodes(false);
  }
  public String getServiceCode()
  {
    return serviceCode;
  }
  public static FedExServiceType getByFullName(String shippingMethod)
  {
    return ObjectUtils.getForMethod(FedExServiceType.values(), shippingMethod, "toString");
  }
  /***********************************************************************/
  /***********************************************************************/
}
