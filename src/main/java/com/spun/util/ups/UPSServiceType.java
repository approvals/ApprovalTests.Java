/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.ups;

import com.spun.util.ObjectUtils;

public enum UPSServiceType {
  UPS_Next_Day_Air_Saver("13"), UPS_2nd_Day_Air("02"), UPS_Ground("03"), UPS_Standard("11"),
  UPS_Express_Early_AM("14"),
  UPS_Next_Day_Air("01"), UPS_Worldwide_Express("07"), UPS_Worldwide_Expedited("08"), UPS_3_Day_Select("12"),
  UPS_Worldwide_Express_Plus("54"), UPS_2nd_Day_Air_AM("59", "UPS 2nd Day Air A.M."), UPS_Express_Saver("65");
  private static String UPS_PREFIX = "UPS ";
  private String fullName;
  private String serviceCode;
  /***********************************************************************/
  private UPSServiceType(String serviceCode)
  {
    this.serviceCode = serviceCode;
    this.fullName = this.toString().replace("_", " ");
  }
  /***********************************************************************/
  private UPSServiceType(String serviceCode, String fullName)
  {
    this.serviceCode = serviceCode;
    this.fullName = fullName;
  }
  /***********************************************************************/
  @Override
  public String toString()
  {
    return fullName == null ? super.toString() : fullName;
  }
  /***********************************************************************/
  public String getServiceCode()
  {
    return serviceCode;
  }
  /***********************************************************************/
  public String getNameForWorldShip()
  {
    return fullName.substring(UPS_PREFIX.length());
  }
  /***********************************************************************/
  public static UPSServiceType getForCode(String code)
  {
    UPSServiceType service = ObjectUtils.getForMethod(values(), code, "getServiceCode");
    if (service == null)
    {
      throw new NullPointerException("no service found for " + code);
    }
    //My_System.variable("service ", service);
    return service;
  }
  /***********************************************************************/
  public static UPSServiceType getByFullName(String serviceType)
  {
    return ObjectUtils.getForMethod(values(), serviceType, "toString");
  }
  /***********************************************************************/
  /***********************************************************************/
}
