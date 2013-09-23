package com.spun.util.ups;

import com.spun.util.ArrayUtils;


public class UPSShipment
{
  private UPSPackage[] packages;
  /***********************************************************************/
  public UPSShipment(UPSPackage[] packages)
  {
    this.packages = packages;
  }
  /***********************************************************************/
  public UPSPackage[] getPackages()
  {
    return packages;
  }
  /***********************************************************************/
  public UPSPackage getMainPackage()
  {
    return ArrayUtils.isEmpty(packages) ? null : packages[0];
  }
  /***********************************************************************/
  /***********************************************************************/
}
